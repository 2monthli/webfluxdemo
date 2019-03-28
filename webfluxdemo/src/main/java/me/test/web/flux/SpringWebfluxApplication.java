package me.test.web.flux;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import reactor.ipc.netty.http.server.HttpServer;

import java.io.IOException;

public class SpringWebfluxApplication {

    public static void main(String[] args) throws IOException {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(WebFluxConfig.class);
        for(String n:applicationContext.getBeanDefinitionNames()){
        	System.out.println(n);
        }
        
        HttpHandler httpHandler = WebHttpHandlerBuilder.applicationContext(applicationContext).build();
        ReactorHttpHandlerAdapter httpHandlerAdapter = new ReactorHttpHandlerAdapter(httpHandler);
        HttpServer.create("localhost",8081).newHandler(httpHandlerAdapter).block();
        System.in.read();
    }
}