package com.example.demo.conf;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Aspect
@Slf4j
public class LockAspect {

    private static final String REDIS_SET_SUCCESS = "OK";

    @Resource
    private CacheUtils cacheUtils;

    @Around("@annotation(lockAnnotation)")
    public Object lockAround(ProceedingJoinPoint joinPoint, LockAnnotation lockAnnotation) throws Throwable {
        ExpressionParser parser = new SpelExpressionParser();
        LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
        EvaluationContext context = new StandardEvaluationContext();

        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        String[] params = discoverer.getParameterNames(signature.getMethod());
        for (int len = 0; len < params.length; len++) {
            context.setVariable(params[len], args[len]);
        }
        Expression expression = parser.parseExpression(lockAnnotation.lockKey());
        String lockKey = expression.getValue(context, String.class);
        int lockTime = lockAnnotation.lockTime() > 1 ? lockAnnotation.lockTime() : 1;
        int waitTime = lockAnnotation.waitTime() > 0 ? lockAnnotation.waitTime() : 0;
        String lockField = lockAnnotation.lockField();
        String randomValue = UUID.randomUUID().toString();
        long startTime = System.currentTimeMillis();
        long endTime = System.currentTimeMillis() + waitTime * 1000;
        try {
            do {
                if (this.getLock(lockField, lockKey, randomValue, lockTime)) {
                    if (log.isInfoEnabled()) {
                        log.info("获得锁成功,方法名为{},参数为{}", joinPoint.getSignature(),
                                String.join("-", Lists.newArrayList(args).stream().map(obj -> JSONObject.toJSONString(ObjectUtils.defaultIfNull(obj, "null")))
                                        .collect(Collectors.toList())));
                    }
                    Object returnObject = joinPoint.proceed(args);
                    return returnObject;
                }
                int sleepTime = Math.min(300, waitTime * 100);
                if (log.isDebugEnabled()) {
                    log.debug("当前无法获得锁,本次等待{}ms,方法名为{},参数为{}", sleepTime, joinPoint.getSignature(),
                            String.join("-", Lists.newArrayList(args).stream().map(obj -> JSONObject.toJSONString(ObjectUtils.defaultIfNull(obj, "null")))
                                    .collect(Collectors.toList())));
                }
                Thread.sleep(sleepTime);
            } while (System.currentTimeMillis() <= endTime);
            if (log.isInfoEnabled()) {
                log.info("获得锁失败,放弃等待,之前共等待{}ms,方法将不执行,方法名为{},参数为{}", System.currentTimeMillis() - startTime, joinPoint.getSignature()
                        , String.join("-", Lists.newArrayList(args).stream().map(Object::toString)
                                .collect(Collectors.toList())));
            }
            return null;
        } finally {
            cacheUtils.delLock(lockField, lockKey, randomValue);
        }
    }
}
