package com.example.demo;

import com.example.demo.conf.EmailEvent;
import com.example.demo.conf.People;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class);
//		//HelloBean hello = (HelloBean) context.getBean("helloBean");
//		//hello.setApplicationContext(context);
//		EmailEvent event = new EmailEvent("hello","boylmx@163.com","this is a email text!");
//		context.publishEvent(event);
//		//System.out.println();
//		People bean = context.getBean(People.class);
//		System.out.println(bean);
	}

}
