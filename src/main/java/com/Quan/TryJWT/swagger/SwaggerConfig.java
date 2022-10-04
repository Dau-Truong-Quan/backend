package com.Quan.TryJWT.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// http://localhost:8080/swagger-ui.html
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2).groupName("public-api")  
        		.apiInfo(apiInfo())
        		.select()                                  
        		.apis(RequestHandlerSelectors.basePackage("com.Quan.TryJWT.controller"))              
        		.build();                                           
    }
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("MiniStore API")
				.description("MiniStore API reference for developers")
				.termsOfServiceUrl("https://www.ministore.com")
				.licenseUrl("ministore@gmail.com").version("1.0").build();
	}
}
