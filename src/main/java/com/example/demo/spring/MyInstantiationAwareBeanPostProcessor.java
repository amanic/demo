package com.example.demo.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.context.annotation.Configuration;

import java.beans.PropertyDescriptor;

@Configuration
public class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

    public MyInstantiationAwareBeanPostProcessor() {
        super();
        System.out
                .println("这是InstantiationAwareBeanPostProcessorAdapter实现类构造器！！");
    }

    // 接口方法、实例化Bean之前调用
    @Override
    public Object postProcessBeforeInstantiation(Class beanClass,
                                                 String beanName) throws BeansException {
        if (beanName.equals("person")) {
            System.out
                    .println("InstantiationAwareBeanPostProcessor调用postProcessBeforeInstantiation方法beanClass = " + beanClass.getName() + "beanName = " + beanName);
        }
        return null;
    }

    // 接口方法、实例化Bean之后调用
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        if (beanName.equals("person")) {
            System.out
                    .println("InstantiationAwareBeanPostProcessor调用postProcessAfterInitialization方法 bean = " + bean.getClass().getName() + "beanName = " + beanName);
        }
        return bean;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
        return null;
    }


}