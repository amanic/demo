package com.example.demo.controller;

import com.example.demo.service.HelloService;
import com.example.demo.service.HelloServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author haitao.chen
 * @date 2020/4/8
 */
@RestController
@Slf4j
public class HelloController {


    @Autowired
    private HelloServiceImpl helloService;

    @RequestMapping("test")
    public String test() {
        log.info("HelloController.test start");
        helloService.executeAsync();
        log.info("HelloController.test end");
        return "success";
    }


    @RequestMapping("test1")
    public String test1() {
        return helloService.dosth();
    }


}
