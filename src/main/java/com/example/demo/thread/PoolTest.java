package com.example.demo.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author haitao.chen
 * @date 2020/4/22
 */
public class PoolTest {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 3, 3, TimeUnit.SECONDS, new LinkedBlockingQueue(),new ThreadPoolExecutor.DiscardPolicy());

        List<Runnable> list = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            final int j = i;
            list.add(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(j);
                }
            });
        }


        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.execute(new Thread(list.get(i), "线程" + i));
        }


    }
}
