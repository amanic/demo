package com.example.demo.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBeanPostProcessor implements BeanPostProcessor {

    public MyBeanPostProcessor() {
        super();
        System.out.println("这是BeanPostProcessor实现类构造器！！");
        // TODO Auto-generated constructor stub
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        if(beanName.equals("person")){
            System.out
                    .println("BeanPostProcessor接口方法postProcessAfterInitialization对属性进行更改！bean = "+bean.getClass().getName()+"beanName = "+beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {

        if(beanName.equals("person")){
            System.out
                    .println("BeanPostProcessor接口方法postProcessBeforeInitialization对属性进行更改！bean = "+bean.getClass().getName()+"beanName = "+beanName);
        }
        return bean;
    }
}