package com.jide.accountservice.infrastructure.configurations;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@EnableSwagger2
@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";

    @Bean
    public Docket api() {
        String version = "1.0";

        final List<ResponseMessage> globalResponses = Arrays.asList(
                new ResponseMessageBuilder().code(HttpStatus.OK.value()).message("Request processed successfully").build(),
                new ResponseMessageBuilder().code(HttpStatus.UNPROCESSABLE_ENTITY.value()).message("Request can not be processed").build(),
                new ResponseMessageBuilder().code(HttpStatus.NO_CONTENT.value()).message("Resource has been deleted").build(),
                new ResponseMessageBuilder().code(HttpStatus.CREATED.value()).message("Resource has been created").build(),
                new ResponseMessageBuilder().code(HttpStatus.BAD_REQUEST.value()).message("Bad Request, Check request details").build(),
                new ResponseMessageBuilder().code(HttpStatus.UNAUTHORIZED.value()).message("Unauthorised request, invalid credential").build(),
                new ResponseMessageBuilder().code(HttpStatus.NOT_FOUND.value()).message("Requested resource not found.").build(),
                new ResponseMessageBuilder().code(HttpStatus.FORBIDDEN.value()).message("Forbidden access to a resource.").build(),
                new ResponseMessageBuilder().code(HttpStatus.CONFLICT.value()).message("Business Logic Conflict. Error due to unfulfilled business rules").build(),
                new ResponseMessageBuilder().code(HttpStatus.PRECONDITION_FAILED.value()).message("Indicates a condition for fulfilling a request failed " +
                        "even though the request is successful. Eg Login on an unregistered device.").build(),
                new ResponseMessageBuilder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message("Oops, Internal Server Error").build()
        );

        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, globalResponses)
                .globalResponseMessage(RequestMethod.POST, globalResponses)
                .globalResponseMessage(RequestMethod.DELETE, globalResponses)
                .globalResponseMessage(RequestMethod.PATCH, globalResponses)
                .globalResponseMessage(RequestMethod.PUT, globalResponses)
                .apiInfo(apiInfo(version))
                .securitySchemes(Lists.newArrayList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.betchess.infrastructure.web.controllers"))
                .paths(PathSelectors.any())
                .build();

    }
    private ApiKey apiKey() {
        return new ApiKey(AUTHORIZATION_HEADER, AUTHORIZATION_HEADER, "header");
    }


    private ApiInfo apiInfo(String version) {
        return new ApiInfoBuilder()
                .title("Betchess Application")
                .description("API services for managing betchess affairs")
                .version(version)
                .build();
    }
//    @Bean
//    public UiConfiguration uiConfig() {
//        return new UiConfiguration(true, false, 1, 1, false, null,
//                 false,  UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS, null);
//    }
////@Bean
//
//public Docket productApi() {
//
//    return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.systemspecs.evoting.infrastructure.web.controllers")).paths(PathSelectors.any())
//
//            .build();
//
//}
}




