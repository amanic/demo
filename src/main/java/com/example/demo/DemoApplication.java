package com.example.demo;

import com.example.demo.javaspi.JavaSerializer;
import com.example.demo.javaspi.KryoSerializer;
import com.example.demo.javaspi.ObjectSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Optional;
import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

@SpringBootApplication(scanBasePackages = "com.example.demo")
public class DemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class);
        ServiceLoader<ObjectSerializer> serializers = ServiceLoader.load(ObjectSerializer.class);

        final Optional<ObjectSerializer> serializer = StreamSupport.stream(serializers.spliterator(), false)
                .findFirst();
        System.out.println("java spi 获取服务：" + serializer.orElse(new KryoSerializer()).getSchemeName());

//		//HelloBean hello = (HelloBean) context.getBean("helloBean");
//		//hello.setApplicationContext(context);
//		EmailEvent event = new EmailEvent("hello","boylmx@163.com","this is a email text!");
//		context.publishEvent(event);
//		//System.out.println();
//		People bean = context.getBean(People.class);
//		System.out.println(bean);
    }

}
