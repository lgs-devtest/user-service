package com.devtest.user.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(SpringDataRestConfiguration.class)
public class SpringFoxConfig {

  @Bean
  public Docket api() {

    ApiInfo apiInfo = new ApiInfo(
        "User Service - Rest API Specification",
        "Collection of Rest API Specification for User Service",
        "1.0",
        "",
        null,
        "Apache 2.0",
        "http://www.apache.org/licenses/LICENSE-2.0",
        Collections.emptyList());

    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any())
        .build()
        .apiInfo(apiInfo)
        .pathMapping("/")
        .useDefaultResponseMessages(false);
  }

}