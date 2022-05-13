package com.delivery.tiago.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

@Value("${prop.swagger.enabled}")
private boolean enableSwagger;	
	
@Bean
public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.enable(enableSwagger)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.delivery.tiago.api.controller.v1"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(metaData());
		}

private ApiInfo metaData() {
		return new ApiInfoBuilder().title("Spring Boot REST API")
				.description("\"Spring Boot REST API Projeto A3 UniFG\"")
				.version("1.0.0").license("Licença Apache Versão 2.0")
				.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0/")
				.build();
		}

}


