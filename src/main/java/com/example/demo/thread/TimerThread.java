package com.example.demo.thread;

import java.time.LocalTime;

public class TimerThread extends Thread {
    @Override
    public void run() {
        while (true) {
            System.out.println(LocalTime.now());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new TimerThread());
        thread.setDaemon(true);
        thread.start();
    }
}