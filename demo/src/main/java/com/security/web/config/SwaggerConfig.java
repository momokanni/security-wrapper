package com.security.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket alipayApi() {

		return new Docket(DocumentationType.SWAGGER_2).groupName("Security安全接口文档") 
				          .apiInfo(apiInfo())
						  .select()
						  .apis(RequestHandlerSelectors.basePackage("com.security.web"))
						  .paths(PathSelectors.any()).build();
	 }

	 private ApiInfo apiInfo() {
		 return new ApiInfoBuilder()
				 .title("Security安全认证")
				 .description("安全服务")
				 .termsOfServiceUrl("http://cheleifeng.com")
				 .contact(new Contact("同行互助", "http://cheleifeng.com", "740949744@qq.com"))
				 .version("1.0").build();
	 }
}
