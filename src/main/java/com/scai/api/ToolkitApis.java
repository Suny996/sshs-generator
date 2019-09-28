package com.scai.api;

import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

public class ToolkitApis {
    public static Docket docs() {
        return new Docket( DocumentationType.SWAGGER_2 ).groupName( "Toolkit" )
                .genericModelSubstitutes( DeferredResult.class )
                .useDefaultResponseMessages( false )
                .forCodeGeneration( true )
                .pathMapping( "/" )
                .select()
                .apis( RequestHandlerSelectors.basePackage( "com.scai" ) )
                .paths( PathSelectors.regex( "/api/.*" ) )
                .build()
                .apiInfo( apiInfo() );
    }

    private static ApiInfo apiInfo() {
        return new ApiInfoBuilder().title( "在线接口文档" )
                .description( "本文档只针对系统中的HTTP接口" )
                .termsOfServiceUrl( "" )
                .version( "20180718" )
                .build();
    }
}
