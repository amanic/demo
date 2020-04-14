package com.example.demo.principle;

import lombok.Data;

/**
 * @author haitao.chen
 * @date 2020/3/30
 */
@Data
public class BusinessMsg {

    private String msgType;

    private String content;

    public BusinessMsg(String msgType, String content) {
        this.msgType = msgType;
        this.content = content;
    }
}
