package com.example.demo.spring;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author haitao.chen
 * @date 2020/5/18
 */
@RestController
@RequestMapping("hello")
public class Mapping {


    @RequestMapping("/test")
    public String dosth(@RequestBody MappingTO mappingTO){
        return "success";
    }
}
