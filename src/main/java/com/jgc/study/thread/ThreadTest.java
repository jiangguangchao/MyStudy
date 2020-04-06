package com.jgc.study.thread;

public class ThreadTest {

	public static void main(String[] args) {
		MyThread myt = new MyThread();
		Thread t1 = new Thread(myt, "窗口1");
		Thread t2 = new Thread(myt, "窗口2");
		t1.start();
		t2.start();
	}

}

class MyThread implements Runnable {

	private int count = 100;

	@Override
	public void run() {
		while (count > 0)
			sale();
	}

	public synchronized void sale() {
		System.out.println("出售第" + (100 - count + 1) + "张车票");
		count--;
	}
}
