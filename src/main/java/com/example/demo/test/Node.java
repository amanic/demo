package com.example.demo.test;

import lombok.Data;

/**
 * @author haitao.chen
 * @date 2020/5/25
 */
@Data
public class Node<T> {

    private T content;

    private Node next;

}
