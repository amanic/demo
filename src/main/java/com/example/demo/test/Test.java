package com.example.demo.test;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author haitao.chen
 * @date 2020/5/25
 */
public class Test {


    public static void main(String[] args) throws IOException {
//        RandomAccessFile accessFile = new RandomAccessFile("/Users/martea/Desktop/test.txt", "rw");
//accessFile.seek(10L);
//        System.out.println(new String(accessFile.readLine().getBytes("ISO-8859-1"),"utf-8"));
//        System.out.println(JSON.toJSONString(accessFile.readLine().getBytes("ISO-8859-1")));
//        System.out.println(JSON.toJSONString("我爱中国".getBytes("ISO-8859-1")));
//
        System.out.println("我爱中国我爱中国我爱中国我爱中国我爱中国我爱中国我爱中国我爱中国我爱中国我爱中国我爱中国我爱中国我爱中国我爱中国我爱中国我爱中国我爱中国我爱中国".length());
    }


    public static void insertNode(Node<Integer> node, Integer contentToInsert) {
        while (null != node.getNext()) {

            if (node.getContent() < contentToInsert) {

            } else {

            }
        }
    }


    public static void findMajorityElement2(int[] arrays) {
        int iii = 1 ^ 3 ^ 5 ^ 7 ^ 9 ^ 1 ^ 3 ^ 5 ^ 9;
        System.out.println(iii);
        // 装载栈的元素
        int candidate = -1;

        // 栈的大小(长度)
        int count = 0;


        // 遍历给出的数组
        for (int i = 0; i < arrays.length; i++) {


            // 判断该栈为空，那么直接将元素入栈
            if (count == 0) {

                candidate = arrays[i];
                count++;

            } else if (candidate == arrays[i]) { // 该元素是否与栈的元素一致-->入栈(栈多一个元素)
                count++;
            } else {
                // 只要不一致-->出栈(栈少一个元素)
                count--;

            }
        }

        // 只要该数字出现次数大于数组长度的2/1，那么留下来的数字肯定在栈顶中
        System.out.println("关注公众号：Java3y--->" + candidate);

    }

}
