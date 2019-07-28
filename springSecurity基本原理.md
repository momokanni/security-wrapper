#### 今天就来跟着源码了解一下security的基本运行原理  

1. 引入依赖  
```  
    <!--Spring Security Oauth2相关-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-oauth2</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>  
```  
2. demo  
```  
  @GetMapping(value = "/hello")
  public String hello() {
     return "hello world";
  }  
```  

在不进行其他自定义配置的情况下，运行并访问该方法时，会弹出Http Basic默认认证框，SpringSecurity默认的固定用户名为：user，密码则在控制台打印输出：  
Using default security password: fb39f959-fe9c-4d51-abb8-7303cfba4d30  
输入完成后，则请求到该方法被得到响应，那么我们来分析一下这其中的运行机制：  

从这个例子里我们可以看到,在默认的情况下,不做任何配置的时候，Spring Security做了这么两件事：  
>1. Spring Security将我们所有的服务都保护起来了,任何一个Rest服务,要想调用都要先进行身份认证。  
>2. 份认证的方式就是上图中的http basic的方式,这个是Spring Security默认的一个行为。  

以上简单的使用，并不足以达到我们的要求，那么如何覆盖SpringSecurity默认行为呢？  
先举个例子： 我们可以提供一个自定义的表单登录  
具体做法：  
    >1. 创建一个类 extends WebSecurityConfigurerAdapter(web安全应用配置的适配器)，可override configure(); 该方法共有三种形式：  
    
```    
            @Configuration
            public class WebConfig extends WebSecurityConfigurerAdapter {
                
                // 接收AuthenticationManagerBuilder的方法：
                protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                    this.disableLocalConfigureAuthenticationBldr = true;
                }
                
                // 接收WebSecurity的方法：
                public void configure(WebSecurity web) throws Exception {
                }  
                
                // 我们重点关注接收参数为HttpSecurity的configure方法，可以看到，它的默认配置正是：
                // 这段默认代码则体现出：默认情况下Spring Security应用的所有请求都需要经过认证，并且认证方式为Http Basic。
                protected void configure(HttpSecurity http) throws Exception {
                    http
                        .authorizeRequests()
                            .anyRequest().authenticated()
                            .and()
                        .formLogin().and()
                        .httpBasic();
                }
            }  
```  
现在我们需要覆盖掉HttpSecurity的方法，因为我们想要自定义一个form表单登录。  
例如：  
```  
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //启用基于表单形式的登录
        http.formLogin()
            .and()
            //下面的配置都是关于授权的配置
            .authorizeRequests()
            //任何请求
            .anyRequest()
            //都需要认证
            .authenticated();
    }  
```  
安全其实就是两件事：一个是认证，一个是授权。  
这时，重启系统，再次访问http://localhost:8080/hello 就会发现跳到了一个表单登录页  同样输入固定用户名：user 和 系统随机产生的pwd实现认证，认证成功后**重定向**到之前的请求，这个也是Spring Security默认的登录成功处理器的一个行为。  

### 在了解了基本配置后，开始谈Security基本原理：  

![security过滤器链](https://github.com/momokanni/SecurityGroup/blob/master/img/2018429172853.png)  

>1. Spring Security的本质就是一组Filter  
>2. FilterSecutrityInterceptor 是整个Spring Security的最后一环,在这个“守门人”的身后，就是我们自己写的服务了  
    因此在这个服务里面，它会去决定我们当前的请求能不能去访问后面的服务。那么它依据什么判断呢？  
    就是依据我们代码里的配置。比如说我们先前的配置：所有的请求都要经过身份认证后才能访问，那么它就会去判断当前的请求是不是经过了前面某一个过滤器的身份认证。当然，前面的那个认证配置其实可以配置得很复杂的，比如说某些请求只有管理员才能访问，那么这些规则都会放在FilterSecutrityInterceptor过滤器里面，这个过滤器会根据这些规则去验证。  
    验证不通过的话，它会根据不能访问的原因抛出不同的异常。  
    
>3. ExceptionTranslationFilter: 捕获FilterSecutrityInterceptor所抛出来的异常,然后这个过滤器会根据抛出来的异常做相应的处理  


#### 查看源码  

![类图](https://github.com/momokanni/SecurityGroup/blob/master/img/security-authentication-Diagram.png)  

UsernamePasswordAuthenticationFilter  
```  
public class UsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
        public UsernamePasswordAuthenticationFilter() {
            super(new AntPathRequestMatcher("/login", "POST")); // 只会处理“/login”的POST请求
        }

        public Authentication attemptAuthentication(HttpServletRequest request,
                HttpServletResponse response) throws AuthenticationException {
            if (postOnly && !request.getMethod().equals("POST")) {
                throw new AuthenticationServiceException(
                        "Authentication method not supported: " + request.getMethod());
            }

            String username = obtainUsername(request);
            String password = obtainPassword(request);

            if (username == null) {
                username = "";
            }

            if (password == null) {
                password = "";
            }

            username = username.trim();

            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                    username, password);

            // Allow subclasses to set the "details" property
            setDetails(request, authRequest);

            return this.getAuthenticationManager().authenticate(authRequest);
        }  
        ...
 }
```  

FilterSecurityInterceptor  
```  
    public class FilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {  
        
        public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
            FilterInvocation fi = new FilterInvocation(request, response, chain);
            invoke(fi);
        }
    
        public void invoke(FilterInvocation fi) throws IOException, ServletException {
            if ((fi.getRequest() != null)
                    && (fi.getRequest().getAttribute(FILTER_APPLIED) != null)
                    && observeOncePerRequest) {
                // filter already applied to this request and user wants us to observe
                // once-per-request handling, so don't re-do security checking
                fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
            }
            else {
                // first time this request being called, so perform security checking
                if (fi.getRequest() != null && observeOncePerRequest) {
                    fi.getRequest().setAttribute(FILTER_APPLIED, Boolean.TRUE);
                }
                // beforeInvocation方法里面的判断逻辑通过以后，执行下面的doFilter == 实际上就是在调后面真正的服务
                // 所以会在调用之前会对绑定到Context(上下文)中的Filter进行认证，认证完成后授权
                InterceptorStatusToken token = super.beforeInvocation(fi); 

                try {
                    fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
                }
                finally {
                    super.finallyInvocation(token);
                }

                super.afterInvocation(token, null);
            }
        }
    }
    ...
```
AbstractSecurityInterceptor  
```  
    protected InterceptorStatusToken beforeInvocation(Object object) {}
```  

建议debug一下整个流程 TODO: 代码执行流程时序图  



