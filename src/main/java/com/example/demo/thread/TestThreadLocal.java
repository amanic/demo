package com.example.demo.thread;

public class TestThreadLocal {

    private static ThreadLocal<String> threadLocal;
    private static ThreadLocal<String> threadLocal1;
    private static ThreadLocal<String> threadLocal2;

    private String string = "";

    public static void main(String[] args) {

        TestThreadLocal testThreadLocal = new TestThreadLocal();
        Thread.currentThread();

        threadLocal = new InheritableThreadLocal<String>() {

            @Override
            protected String initialValue() {
                return "初始化值";
            }

        };
        
        for (int i = 0; i < 10; i++){
            new Thread(new MyRunnable(), "线程"+i).start();
        }

    }

    public static class MyRunnable implements Runnable {

        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            System.out.println(name + "的threadLocal"+ ",设置为" + name);
            threadLocal.set(name);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
            System.out.println(name + ":" + threadLocal.get());
        }
//        private TestThreadLocal testThreadLocal;
//
//        public MyRunnable(TestThreadLocal testThreadLocal) {
//            this.testThreadLocal = testThreadLocal;
//        }
//
//        @Override
//        public void run() {
//            String name = Thread.currentThread().getName();
//            testThreadLocal.string = name;
//            try {
//                Thread.yield();
//            } catch (Exception e) {}
//            System.out.println(name + ":" + testThreadLocal.string);
//        }

    }

}