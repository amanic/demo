package com.example.demo.conf;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SurvivalClamProcessor implements Runnable {

    public static void main(String[] args) {
        SurvivalClamProcessor survivalClamProcessor
                = new SurvivalClamProcessor(lockField, lockKey, randomValue, lockTime);
        Thread survivalThread = new Thread(survivalClamProcessor);
        survivalThread.setDaemon(Boolean.TRUE);
        survivalThread.start();
        Object returnObject = joinPoint.proceed(args);
        survivalClamProcessor.stop();
        survivalThread.interrupt();
        return returnObject;
    }

    private static final int REDIS_EXPIRE_SUCCESS = 1;

    SurvivalClamProcessor(String field, String key, String value, int lockTime) {
        this.field = field;
        this.key = key;
        this.value = value;
        this.lockTime = lockTime;
        this.signal = Boolean.TRUE;
    }

    private String field;

    private String key;

    private String value;

    private int lockTime;

    //线程关闭的标记
    private volatile Boolean signal;

    void stop() {
        this.signal = Boolean.FALSE;
    }

    @Override
    public void run() {
        int waitTime = lockTime * 1000 * 2 / 3;
        while (signal) {
            try {
                Thread.sleep(waitTime);
                if (cacheUtils.expandLockTime(field, key, value, lockTime) == REDIS_EXPIRE_SUCCESS) {
                    if (log.isInfoEnabled()) {
                        log.info("expandLockTime 成功，本次等待{}ms，将重置锁超时时间重置为{}s,其中field为{},key为{}", waitTime, lockTime, field, key);
                    }
                } else {
                    if (log.isInfoEnabled()) {
                        log.info("expandLockTime 失败，将导致SurvivalClamConsumer中断");
                    }
                    this.stop();
                }
            } catch (InterruptedException e) {
                if (log.isInfoEnabled()) {
                    log.info("SurvivalClamProcessor 处理线程被强制中断");
                }
            } catch (Exception e) {
                log.error("SurvivalClamProcessor run error", e);
            }
        }
        if (log.isInfoEnabled()) {
            log.info("SurvivalClamProcessor 处理线程已停止");
        }
    }
}
