package com.example.demo.test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class PrintThread implements Runnable{

    private CyclicBarrier cb;

    public PrintThread(CyclicBarrier cycb){
        this.cb = cycb;
    }


    @Override
    public void run(){
        for(int i = 0; i<10;i++){
            for(int j = 1; j<=10;j++){
                System.out.println(Thread.currentThread().getName()+"输出"+(i*10+j));
            }
            try {
                cb.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }


}