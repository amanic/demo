package com.example.demo.controller;

import com.example.demo.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author haitao.chen
 * @date 2020/4/8
 */
@RestController
@Slf4j
public class HelloController {


    @Resource
    private HelloService helloService;

    @RequestMapping("test")
    public String test(){
        log.info("HelloController.test start");
        helloService.executeAsync();
        log.info("HelloController.test end");
        return "success";
    }


}
