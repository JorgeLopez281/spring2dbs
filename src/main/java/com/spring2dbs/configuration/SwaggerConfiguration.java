package com.spring2dbs.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.spring2dbs.controller.ClientRestController;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@ComponentScan(basePackageClasses = ClientRestController.class)
@Configuration
public class SwaggerConfiguration {

	private static final String SWAGGER_API_VERSION = "1.0";
	private static final String LICENCE_TEXT = "MIT License";
	private static final String TITLE = "Spring2db API RestFull";
	private static final String DESCRIPTION = "Documentacion Swagger del API RestFull de ejercicio Practico del grupo de estudio Spring/Pragma";

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title(TITLE)
				.description(DESCRIPTION)
				.version(SWAGGER_API_VERSION)
				.license(LICENCE_TEXT)
				.build();
	}

	
	/**
	 * El método spring2dbApi(), es el método principal y el que le indica a swagger 
	 * que todos nuestros endpoint REST los “mapee” y 
	 * documente construyendo la ayuda para ellos, además le indica que la 
	 * Información general del api la lea desde el método apiInfo() que hemos 
	 * implementado.
	 * 
	 * @return
	 */
	@Bean
	public Docket spring2dbApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.pathMapping("/")
				.select()
				.paths(PathSelectors.regex("/spring2db.*"))
				.build();
	}

}
