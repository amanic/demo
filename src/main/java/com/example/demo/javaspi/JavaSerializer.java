package com.example.demo.javaspi;

/**
 * @author haitao.chen
 * @date 2020/5/6
 */
public class JavaSerializer implements ObjectSerializer {
    @Override
    public byte[] serialize(Object obj) throws Exception {
        byte[] bytes;
        System.out.println("调用了JavaSerializer的serialize方法");
        bytes = "".getBytes();
        return bytes;
    }

    @Override
    public <T> T deSerialize(byte[] param, Class<T> clazz) throws Exception {
        T object;
        System.out.println("调用了JavaSerializer的deSerialize方法");
        object = (T) new Object();
        return object;
    }

    @Override
    public String getSchemeName() {
        return "javaSerializer";
    }
}
