package com.reading.msleitura.infrastructure.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Carlos H. de Oliveira - carlos.h.oliveira@cho.eti.br
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)//
				.select()//
				.apis(RequestHandlerSelectors.basePackage("com.reading.msleitura.api"))//
				.paths(PathSelectors.any())//
				.build()//
				.useDefaultResponseMessages(false)//
				.globalResponseMessage(RequestMethod.GET, responseMessageForGET())//
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()//
				.title("Reading")//
				.description("A aplicação foi desenvolvida utilizando Spring Boot, Java 11 e outras tecnologias.")//
				.version("1.0.0")//
				.license("Apache License Version 2.0")//
				.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")//
				.build();
	}

	private List<ResponseMessage> responseMessageForGET() {
		return new ArrayList<ResponseMessage>() {
			private static final long serialVersionUID = 1L;

			{
				add(new ResponseMessageBuilder()//
						.code(500)//
						.message("Unknown exception")//
						.responseModel(new ModelRef("string"))//
						.build());//
			}
		};
	}

}
