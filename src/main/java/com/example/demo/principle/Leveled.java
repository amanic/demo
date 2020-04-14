package com.example.demo.principle;

import com.alibaba.fastjson.JSON;

/**
 * @author haitao.chen
 * @date 2020/3/30
 */
public abstract class Leveled implements LeveledHandler {


    protected String name;

    protected String msgType;

    protected Leveled nextHandler;

    public Leveled(String name, String msgType, Leveled nextHandler) {
        this.name = name;
        this.msgType = msgType;
        this.nextHandler = nextHandler;
    }

    @Override
    public String handleMsg(BusinessMsg msg) {
        if (msg.getMsgType().equals(this.msgType)) {
            System.out.println(this.name + "处理了消息，消息内容是" + JSON.toJSONString(msg));
            getContent(msg);
        } else {
            if (this.nextHandler != null) {
                this.nextHandler.handleMsg(msg);
            } else {
                System.out.println("没有找到合适的处理人");
            }
        }
        return null;
    }


}
