package com.example.demo.service.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author haitao.chen
 * @date 2020/6/9
 */

@Component
@Aspect
@Slf4j
@Order(value = 2)
public class Aspect2 {

    @Around("execution(public * com.example.demo.service.* ..*(..))")
    public Object process(ProceedingJoinPoint joinPoint) {
        try {
            System.out.println("Aspect2");
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            return null;
        }
    }

}
