package com.example.demo.test;

import java.util.concurrent.CyclicBarrier;

/**
 * 问题：有三个线程，每个线程分别从1打印到100，现在要求10个一批10个一批的打印，
 *  也就是说必须三个线程都打印完前十个，每个线程才能继续打印下一个，
 *  如果有某个线程已经打印完了前十个，其他线程没打印完，则必须等待
 * @author haitao.chen
 * @date 2020/7/10
 */
public class Test1 {
    public static void main(String[] args) {

        CyclicBarrier cb = new CyclicBarrier(3);
        new Thread(new PrintThread(cb),"线程一").start();
        new Thread(new PrintThread(cb),"线程二").start();
        new Thread(new PrintThread(cb),"线程三").start();

        int i =1;
        while(i<10){
            if(cb.isBroken()){
                cb.reset();
                i++;
            }
        }

        System.exit(0);
    }

}






