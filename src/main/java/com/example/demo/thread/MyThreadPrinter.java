package com.example.demo.thread;

/**
 * Created by Andrew  on 2017/3/26.
 */
public class MyThreadPrinter implements Runnable {
    private String name;
    private Object prev;
    private Object self;

    private MyThreadPrinter(String name, Object prev, Object self) {
        this.name = name;
        this.prev = prev;
        this.self = self;
    }

    public static void main(String[] args) throws Exception {
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();
        MyThreadPrinter pa = new MyThreadPrinter("A", c, a);
        MyThreadPrinter pb = new MyThreadPrinter("B", a, b);
        MyThreadPrinter pc = new MyThreadPrinter("C", b, c);


        new Thread(pa).start();
        new Thread(pb).start();
        new Thread(pc).start();
    }

    @Override
    public void run() {
        int count = 10;
        while (count > 0) {
            synchronized (prev) {
                synchronized (self) {
                    System.out.print(name);
                    count--;

                    self.notify();
                }
                try {
                    prev.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}