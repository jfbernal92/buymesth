package com.buymesth.app.security;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;


@Configuration
@EnableSwagger2
public class SwaggerConfiguration {


    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>(Collections.singletonList("application/json"));

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES).select().paths(PathSelectors.any())
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot"))).build()
                //    .globalResponseMessage(RequestMethod.GET, defaultGlobalResponseMessage())
                .securitySchemes(Arrays.asList(apiKey())).securityContexts(Arrays.asList(securityContext()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("").build();
    }

    private ApiKey apiKey() {
        return new ApiKey("apiKey", "Authorization", "header");
    }

    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder().scopeSeparator(",").additionalQueryStringParams(null)
                .useBasicAuthenticationWithAccessCodeGrant(false).build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("apiKey", authorizationScopes));
    }

    /**
     * ResponseModel configuration for swagger
     */
    private List<ResponseMessage> defaultGlobalResponseMessage() {
        return Arrays.asList(
                new ResponseMessageBuilder().code(201).message("Created").build(),
                new ResponseMessageBuilder().code(204).message("No Content").build(),
                new ResponseMessageBuilder().code(401).message("Unauthorized").build(),
                new ResponseMessageBuilder().code(403).message("Forbidden").build(),
                new ResponseMessageBuilder().code(404).message("Not Found").build(),
                new ResponseMessageBuilder().code(409).message("Conflict").build(),
                new ResponseMessageBuilder().code(422).message("Unprocessable Entity").build(),
                new ResponseMessageBuilder().code(500).message("Internal Server Error").build());
    }
}