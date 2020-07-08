package com.example.demo.seckill.delayq.usage;

/**
 * 具体执行相关业务的业务类
 * @author martea
 * @date 2020年07月08日17:02:20
 */
public class DelayOrderWorker  implements Runnable {
 
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//相关业务逻辑处理
		System.out.println(Thread.currentThread().getName()+" do something ……");
	}
}