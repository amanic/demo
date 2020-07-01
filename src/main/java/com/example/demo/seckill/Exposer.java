package com.example.demo.seckill;

import lombok.Data;

@Data
public class Exposer {


    //加密措施
    private String md5;

    //其中必要字段，如是否开启秒杀，时间等省


    public Exposer(String md5) {
        this.md5 = md5;
    }
}