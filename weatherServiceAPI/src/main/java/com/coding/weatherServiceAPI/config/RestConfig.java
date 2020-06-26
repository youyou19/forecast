package com.coding.weatherServiceAPI.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.any;

@Configuration
@EnableSwagger2
public class RestConfig {

  @Bean
  public RestTemplate restTemplate() {
  return  new RestTemplate();
  }
    
  @Autowired
  public ObjectMapper  objectMapper(){
  return new ObjectMapper();
  }
//
//  @Bean
//  public Docket api(){
//  return new Docket(DocumentationType.SWAGGER_2).groupName("Weather").select()
//   .apis(RequestHandlerSelectors.basePackage("com.coding.weatherServiceAPI.controller"))
//   .paths(any()).build().apiInfo(new ApiInfo("Weather Services",
//   "A set of services to provide data access to Weather through the zip code", "1.0.0", null,
//   new Contact("Jean Vilaire", "https://www.test.com/", "jeanvilaire.octavius@gmail.com"), null,null));
//  }

  @Bean
  public Docket api() {
      return new Docket(DocumentationType.SWAGGER_2).select()
          .apis(RequestHandlerSelectors
              .basePackage("com.coding.weatherServiceAPI.controller"))
          .paths(PathSelectors.regex("/.*"))
          .build().apiInfo(apiEndPointsInfo());
  }

  private ApiInfo apiEndPointsInfo() {

      return new ApiInfoBuilder().title("Documentation API")
          .description("Weather REST API")
          .contact(new Contact("Jean Vilaire Octavius", "https://github.com/vocsi130", "jeanvilaire.octavius@gmail.com"))
          .license("Apache 2.0")
          .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
          .version("1.0.0")
          .build();
  }
}
