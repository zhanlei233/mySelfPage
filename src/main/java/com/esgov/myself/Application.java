package com.esgov.myself;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Boot 应用启动类
 * @author
 *
 * */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application{


    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

}