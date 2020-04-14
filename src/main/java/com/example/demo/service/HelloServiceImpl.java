package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
}
