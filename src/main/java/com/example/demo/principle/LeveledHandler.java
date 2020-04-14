package com.example.demo.principle;

/**
 * @author haitao.chen
 * @description 处理器
 * @date 2020/3/30
 */
public interface LeveledHandler {


    String handleMsg(BusinessMsg msg);

    String getContent(BusinessMsg msg);

}
