package com.example.demo.test;

/**
 * 单例模式 holder
 */
public class Singleton3 {
  private static class SingletonHolder {
    private static final Singleton3 singleton = new Singleton3();
    private SingletonHolder() {
      System.out.println("SingletonHolder初始化");
    }
  }
  private Singleton3() {
    System.out.println("Singleton3初始化");
  }

  /**
  * 勘误：多写了个synchronized。。
  public synchronized static Singleton3 getInstance() {
    return SingletonHolder.singleton;
  }
  */
  public static Singleton3 getInstance() {
    return SingletonHolder.singleton;
  }

  public static void main(String[] args) {
//    Singleton3.getInstance();
    System.out.println("do nothing。。。");
  }
}