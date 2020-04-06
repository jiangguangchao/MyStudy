package com.jgc.study.thread.synchroniz.base.test2;

public class TestMain {

	public static void main(String[] args) {
		TestObj obj1 = new TestObj();
		// TestObj obj2 = new TestObj();
		Thread t1 = new Thread(obj1, "线程1");
		Thread t2 = new Thread(obj1, "线程2");
		t1.start();
		t2.start();
	}
}

class TestObj extends Thread {

	private volatile int count = 0;

	@Override
	public void run() {
		while (count < 100) {
			try {
				Thread.sleep(10);
			} catch (Exception e) {
				// TODO: handle exception
			}
			sale();
		}

	}

	private void sale() {
		if (count < 100) {
			count++;
			System.out.println(Thread.currentThread().getName() + ", count: " + count);
		}

	}

}
