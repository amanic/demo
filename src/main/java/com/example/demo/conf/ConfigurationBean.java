package com.example.demo.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author haitao.chen
 * @date 2020/3/4
 */
@Configuration
public class ConfigurationBean {

//    @Bean
//    public EmailEvent getEmail(){
//        return new EmailEvent("hello","boylmx@163.com","this is a email text!");
//    }
    @Bean
    public People me(){
        return new People();
    }
}
