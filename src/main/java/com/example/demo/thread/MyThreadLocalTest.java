package com.example.demo.thread;

/**
 * @author haitao.chen
 * @date 2020/5/21
 */
public class MyThreadLocalTest {
    private static String noneThrealLocal = "";

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    private static ThreadLocal<String> threadLocal1 = new ThreadLocal<>();

    public static void main(String[] args) {
        test1();
    }

    public static void test0() {
        for (int i = 0; i < 10; i++) {

            new Thread(() -> {
                noneThrealLocal = Thread.currentThread().getName();
                threadLocal.set(Thread.currentThread().getName());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"noneThrealLocal内容"+noneThrealLocal);
                System.out.println(Thread.currentThread().getName()+"threadLocal内容"+threadLocal.get());
            }, "线程" + i).start();
        }
    }

    public static void test1() {
        threadLocal.set(100+"");
        threadLocal1.set(100+"");
        Thread thread = Thread.currentThread();
        threadLocal.get();
        System.out.println(0);
    }
}
