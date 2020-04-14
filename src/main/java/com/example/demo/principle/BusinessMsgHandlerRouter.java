package com.example.demo.principle;

/**
 * @author haitao.chen
 * @date 2020/3/30
 */
public class BusinessMsgHandlerRouter {


    public static void main(String[] args) {
        BLeveled bLeveled = new BLeveled("bbb", "bbb", null);
        ALeveled aLeveled = new ALeveled("aaa", "aaa", bLeveled);


        aLeveled.handleMsg(new BusinessMsg("bbb", "bbb内容"));
        aLeveled.handleMsg(new BusinessMsg("aaa", "aaa内容"));
        aLeveled.handleMsg(new BusinessMsg("ccc", "ccc内容"));
    }
}
