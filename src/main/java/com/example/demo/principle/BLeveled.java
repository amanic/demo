package com.example.demo.principle;

/**
 * @author haitao.chen
 * @date 2020/3/30
 */
public class BLeveled extends Leveled{


    public BLeveled(String name, String msgType, Leveled nextHandler) {
        super(name, msgType, nextHandler);
    }

    @Override
    public String getContent(BusinessMsg msg) {
        System.out.println("B处理。。。");
        return null;
    }
}
