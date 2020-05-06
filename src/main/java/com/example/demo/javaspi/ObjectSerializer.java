package com.example.demo.javaspi;

public interface ObjectSerializer {

    byte[] serialize(Object obj) throws Exception;

    <T> T deSerialize(byte[] param, Class<T> clazz) throws Exception;

    String getSchemeName();
}