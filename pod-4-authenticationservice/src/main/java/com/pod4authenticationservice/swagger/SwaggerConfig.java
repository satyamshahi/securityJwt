package com.pod4authenticationservice.swagger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * This class is for Swagger configuration
 *
 */
@Configuration
@EnableSwagger2
@Slf4j
public class SwaggerConfig {
   
	
	/**
     * swagger's docker api method returns the swagger build
     */
    @Bean
	public Docket api() {
    	log.info("api method is started...");
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}
}