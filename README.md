## 8月要出一版基于该框架的权限管理系统  
### 前情提要  
在这首先感谢毅哥的支持，其中包括在架构和关键技术痛点的经验分享和提醒，弥补了我经验不足带来的缺失、以及给予充分自由的发挥空间。🥶🥶🥶🥶🥶#不是拍马屁#  
### 先推荐几个好用的chrome插件  
>1. [Sourcegraph](https://zhuanlan.zhihu.com/p/27620085)  
>2. [Clear Cache](https://chrome.google.com/webstore/detail/clear-cache/cppjkneekbjaeellbfkmgnhonkkjfpdn?hl=zh-CN)：一键清理浏览器缓存  
>3. [科学上网](http://googlehelper.net/)  
>4. [JSONView](https://www.jianshu.com/p/6ea9f2245f4d)  
>5. [AdBlock屏蔽页面广告](https://getadblock.com/)  
>6. [The Great Suspender](https://chrome.google.com/webstore/detail/the-great-suspender/klbibkeccnjlkjkiokjodocebajanakg?hl=zh-CN)  
>7. [Octotree](https://zhuanlan.zhihu.com/p/20431851)  
### 代码规范很重要 [阿里代码规范idea插件](https://github.com/alibaba/p3c/tree/master/idea-plugin)  
### 编写初心  
>1. 为了深入了解安全防护的知识  
>2. 为了读源码
>2. 为了解决springSecurity上手难和使用配置繁杂(类似于Lucence和Elasticsearch的关系)  
>3. 为了提升安全相关的开发效率，期望能做到使用者傻瓜式开发  

### 功能介绍  
该组件完全基于springSecurity进行的二次开发。  
提供基于OAuth2的身份认证和访问授权、单点登录、文件上传、权限系统无障碍接入、jwt(增强)、三方登录(绑定、解绑)、接口防刷、特殊接口隐藏、反爬虫、XSS、CSRF、SQL注入、websocket通信安全、日志管理、Mock伪服务创建及管理、基于压力测试的接口优化等一系列安全场景的解决方案  
### 框架结构  
app : 移动端  
browser : PC web  
core : 核心包  
demo : 样例，方便使用者更快熟悉该组件  
### 依赖关系  
```
            app -->  
  demo -->              core --> springSecurity  
            browser --> 
```  
### 引用方式  
>1. 开源引用，可直接在demo模块进行开发，也可创建子模块，可修改源代码  
>2. maven引用  
>3. 可直接运行做成服务，对外提供API  
### 技术栈  
>1. 支持前后端分离  
>2. springBoot2.0.8、SpringSecurity5.0.11、Spring5.0.12、lombok、junit4.12、logback、swagger 
### 后续更新  
TODO: 单点登录组件  
TODO: 接口类防护  
TODO: 文件上传安全防护  
TODO: XSS、CSRF  
TODO: websocket安全通信  
TODO: 权限管理组件  
TODO: 引入docker  
这么看来，啥都没做🤣🤣🤣就是一个空壳子  

### 想要完全理解springSecurity的架构及编码思路需要看一下[《OAuth2实战》](https://github.com/momokanni/OAuth2)这本书  

## SpringSecurity简介  

```
   Spring Security is a powerful and highly customizable authentication and access-control framework. 
   It is the de-facto standard for securing Spring-based applications.  

   Spring Security is a framework that focuses on providing both authentication 
   and authorization to Java applications. Like all Spring projects, 
   the real power of Spring Security is found in how easily it can be extended to 
   meet custom requirements
```  
译：Spring Security是一个强大而且可高度自定义的认证和流程控制框架。也是一套保护spring基础应用的安全标准。  
   Spring Security是一个聚焦于对Java应用程序提供认证和授权的框架，和其他spring项目一样，其强大之处在于能够很简单的进行自定义开发,dei 我们做的就是自定义开发。  
   
#### 统一的登录认证流程
![认证流程](https://github.com/momokanni/doc-repository/blob/master/security-wrapper/img/process/%E8%AE%A4%E8%AF%81%E6%B5%81%E7%A8%8B%E6%A2%B3%E7%90%86(%E8%A1%A8%E5%8D%95%E7%99%BB%E5%BD%95).svg)  

#### 表单登录处理流程  

![默认表单登录处理流程](https://github.com/momokanni/doc-repository/blob/master/security-wrapper/img/process/%E8%A1%A8%E5%8D%95%E9%BB%98%E8%AE%A4%E5%A4%84%E7%90%86%E6%B5%81%E7%A8%8B.png)  

#### springSocial三方登录处理流程  
![springSocial三方登录](https://github.com/momokanni/doc-repository/blob/master/security-wrapper/img/process/springSocial%E7%A4%BE%E4%BA%A4%E7%99%BB%E5%BD%95%E5%A4%84%E7%90%86%E6%B5%81%E7%A8%8B.png)  

#### 记住我-登录成功处理流程  

![记住我-登录成功处理流程](https://github.com/momokanni/doc-repository/blob/master/security-wrapper/img/process/%E8%AE%B0%E4%BD%8F%E6%88%91-%E7%99%BB%E5%BD%95%E6%88%90%E5%8A%9F%E5%A4%84%E7%90%86%E6%B5%81%E7%A8%8B.svg)  

#### 记住我-自动登录  

![记住我-自动登录](https://github.com/momokanni/doc-repository/blob/master/security-wrapper/img/process/%E8%AE%B0%E4%BD%8F%E6%88%91-%E8%87%AA%E5%8A%A8%E7%99%BB%E5%BD%95.svg) 


