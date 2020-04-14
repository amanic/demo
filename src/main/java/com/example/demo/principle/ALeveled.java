package com.example.demo.principle;

/**
 * @author haitao.chen
 * @date 2020/3/30
 */
public class ALeveled extends Leveled{


    public ALeveled(String name, String msgType, Leveled next) {
        super(name,msgType,next);
    }

    @Override
    public String getContent(BusinessMsg msg) {
        System.out.println("A处理。。。");
        return null;
    }
}
