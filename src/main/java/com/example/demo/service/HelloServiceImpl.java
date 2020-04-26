package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author haitao.chen
 * @date 2020/4/8
 */

@Service
@Slf4j
public class HelloServiceImpl implements HelloService{


    @Override
    @Async("asyncServiceExecutor")
    public void executeAsync() {
        log.info("start executeAsync");
        try{
            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();
        }
        log.info("end executeAsync");
    }

    public static void main(String[] args) {

        Callable<Integer> callable = new Callable<Integer>() {

            @Override
            public Integer call() throws Exception {
                Thread.sleep(3000);
                return 1;
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<Integer> submit = executorService.submit(callable);

        try {
            System.out.println(submit.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(125123);


    }
}
