package com.jgc.study.thread.synchroniz.base.test1;

/**
 * @Description:
 *
 * @author jgc
 *
 * @date:2020年2月20日 下午3:57:52
 */
public class TestObj {

	private static String stcStr = "s1";

	private String str = "s2";

	private int num = 0;

	/**
	 * 普通方法
	 */
	public void method() {
		for (int i = 0; i < 100; i++) {
			try {
				Thread.sleep(10);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + ", i: " + i);
		}
	}

	/**
	 * 方法上添加synchronized修饰，当程序运行到该方法内时，该方法所在的对象加锁，
	 * 直到该方法运行完毕才会释放对象锁。
	 * @throws InterruptedException 
	 * 
	 */
	public synchronized void method1() {
		for (int i = 0; i < 100; i++) {
			try {
				Thread.sleep(10);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + ", i: " + i);
		}
	}

	public synchronized void method1_1() {
		for (int i = 0; i < 100; i++) {
			try {
				Thread.sleep(10);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + ", i: " + i);
		}
	}

	/**
	 * 静态方法加锁，当程序运行到该方法内时，该方法所在的类加锁，该类的其他方法或者
	 * 属性无法被其他线程操作，直到当前方法运行完，类锁解除。
	 */
	public synchronized static void method2() {
		for (int i = 0; i < 100; i++) {
			try {
				Thread.sleep(10);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + ", i: " + i);
		}
	}

	public synchronized static void method2_1() {
		for (int i = 0; i < 100; i++) {
			try {
				Thread.sleep(10);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + ", i: " + i);
		}
	}

	/**
	 * synchronized代码块，这个方法和method5一样吗？
	 */
	public void method3() {
		synchronized (str) {
			for (int i = 0; i < 100; i++) {
				try {
					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + ", i: " + i);
			}
		}

	}

	/**
	 * synchronized代码块，由于stcStr是静态属性，因此程序运行到代码块中时，类加锁。直到代码块运行完毕，类锁才解除。
	 */
	public void method4() {
		synchronized (stcStr) {
			for (int i = 0; i < 100; i++) {
				try {
					Thread.sleep(10);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + ", i: " + i);
			}
		}

	}

	public void method4_1() {
		synchronized (stcStr) {
			for (int i = 0; i < 100; i++) {
				try {
					Thread.sleep(10);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + ", i: " + i);
			}
		}

	}

	/**
	 * synchronized代码块，由于str是对象属性，因此程序运行到代码块中时，对象加锁。直到代码块运行完毕，对象锁才解除。
	 */
	public void method5() {
		synchronized (this) {
			for (int i = 0; i < 100; i++) {
				try {
					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + ", i: " + i);
			}
		}

	}

	public void method5_1() {
		synchronized (this) {
			for (int i = 0; i < 100; i++) {
				try {
					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + ", i: " + i);
			}
		}

	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}
