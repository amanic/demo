package com.example.demo.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Configuration;

/**
 * @author haitao.chen
 * @date 2020/4/26
 */
@Configuration
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    public MyBeanFactoryPostProcessor() {
        super();
        System.out.println("MyBeanFactoryPostProcessor初始化");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("MyBeanFactoryPostProcessor接口方法postProcessBeanFactory对属性进行更改！");
        BeanDefinition bd = configurableListableBeanFactory.getBeanDefinition("person");
        bd.getPropertyValues().addPropertyValue("phone", "110");
    }
}
