### 前情提要  
在这首先感谢毅哥的支持，其中包括在架构思路和关键技术点的经验分享，弥补了我经验不足带来的缺失、以及给予充分自由的发挥空间。🥶🥶🥶🥶🥶#不是拍马屁#  
### 基本介绍  
基于springSecurity并对其进行封装二次开发。  
提供基于OAuth2的身份认证和访问授权、单点登录、文件上传、权限系统无障碍接入、三方登录、接口防刷、特殊接口隐藏、反爬虫、XSS、CSRF、SQL注入、websocket通信安全、Mock伪服务创建及管理、压力测试优化等一系列安全场景的解决方案  
### 代码结构  
app : 移动端  
browser : PC web  
core : 核心包  
demo : 样例服务，方便使用者更快熟悉该组件  
### 依赖关系  
```
            app -->  
  demo -->              core --> springSecurity  
            browser --> 
```
### 技术栈  

### 关注点  

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
   Spring Security是一个聚焦于对Java应用程序提供认证和授权的框架，和其他spring项目一样，其强大之处在于能够很简单的进行自定义开发

#### springSecurity默认的处理流程  

![默认处理流程](https://github.com/momokanni/doc-repository/blob/master/security-wrapper/img/process/%E8%A1%A8%E5%8D%95%E9%BB%98%E8%AE%A4%E5%A4%84%E7%90%86%E6%B5%81%E7%A8%8B.png)  

#### 认证流程(登录)
![认证流程](https://github.com/momokanni/doc-repository/blob/master/security-wrapper/img/process/%E8%AE%A4%E8%AF%81%E6%B5%81%E7%A8%8B%E6%A2%B3%E7%90%86(%E8%A1%A8%E5%8D%95%E7%99%BB%E5%BD%95).svg)  

#### 记住我-登录成功处理流程  

![记住我-登录成功处理流程](https://github.com/momokanni/doc-repository/blob/master/security-wrapper/img/process/%E8%AE%B0%E4%BD%8F%E6%88%91-%E7%99%BB%E5%BD%95%E6%88%90%E5%8A%9F%E5%A4%84%E7%90%86%E6%B5%81%E7%A8%8B.svg)  

#### 记住我-自动登录  

![记住我-自动登录](https://github.com/momokanni/doc-repository/blob/master/security-wrapper/img/process/%E8%AE%B0%E4%BD%8F%E6%88%91-%E8%87%AA%E5%8A%A8%E7%99%BB%E5%BD%95.svg) 


