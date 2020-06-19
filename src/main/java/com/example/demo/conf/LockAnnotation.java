package com.example.demo.conf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface LockAnnotation {

    /**
     * 加锁的key的前缀
     *
     * @return
     */
    String lockField() default "";
    
    /**
     * 加锁的key的值
     * @return
     */
    String lockKey() default "";

    /**
     * 锁自动释放时间，单位s
     *
     * @return
     */
    int lockTime() default 3;

    /**
     * 获取锁的最大等待时间，单位s，默认不等待，0即为快速失败
     *
     * @return
     */
    int waitTime() default 0;
}

