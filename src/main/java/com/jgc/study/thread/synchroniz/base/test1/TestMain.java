package com.jgc.study.thread.synchroniz.base.test1;

public class TestMain {

	public static void main(String[] args) {
		TestMain m = new TestMain();
		// m.f1();// 两个线程执行同一个对象的同一个方法（方法加synchronized）
		// m.f1_1();// 两个线程执行两个对象的同一个方法（方法加synchronized）
		// m.f1_2();// 两个线程执行一个对象的两个方法（方法都加synchronized）

		/*
		 * 两个线程执行一个对象的两个方法（一个方法加synchronized，另一个不加）
		 * 结果: 两个方法交替执行，说明被synchronized修饰的方法之间才会竞争或互斥，被synchronized修饰
		 * 的方法和不被修饰的方法之间不会竞争或互斥
		 */
		// m.f1_3();

		// m.f2();// 两个线程执行同一个对象的同一个静态方法（方法加synchronized）
		// m.f2_1();// 两个线程执行两个对象的同一个静态方法（方法加synchronized）
		// m.f2_2();// 两个线程执行两个对象的同一个静态方法（方法加synchronized）
		// m.f2_3();// 两个线程执行两个对象的两个方法（ 方法都加synchronized，一个静态方法，一个非静态）
		// m.f2_4();// 两个线程执行同一个对象的两个方法（ 方法都加synchronized，一个静态方法，一个非静态）

		// m.f3();// 两个线程执行同一个对象的同一个方法（方法中有synchronized代码块，且synchronized参数是对象属性）
		// m.f31();// 为什么运行结果和f51不一样？？？？？？？？

		// m.f4();// 两个线程执行同一个对象的同一个方法（方法中有synchronized代码块，且synchronized参数是类属性）
		// m.f41();// 两个线程执行两个对象的同一个方法（方法中有synchronized代码块，且synchronized参数是类属性）

		// m.f5();
		// m.f5_1();
		// m.f5_2();
		m.f6();
	}

	/**
	 * 两个线程执行同一个对象的同一个方法（方法加synchronized）
	 */
	public void f1() {
		final TestObj obj = new TestObj();

		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				obj.method1();
			}

		}, "线程1");

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				obj.method1();
			}
		}, "线程2");

		t1.start();
		t2.start();
	}

	/**
	 * 两个线程执行两个对象的同一个方法（方法加synchronized）
	 */
	public void f1_1() {
		final TestObj obj1 = new TestObj();
		final TestObj obj2 = new TestObj();

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				obj1.method1();
			}
		}, "线程1");

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				obj2.method1();
			}
		}, "线程2");

		t1.start();
		t2.start();
	}

	/**
	 * 两个线程执行一个对象的两个方法（方法都加synchronized）
	 */
	public void f1_2() {
		final TestObj obj1 = new TestObj();

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				obj1.method1();
			}
		}, "线程1");

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				obj1.method1_1();
			}
		}, "线程2");

		t1.start();
		t2.start();
	}

	/**
	 * 两个线程执行一个对象的两个方法（一个方法加synchronized，另一个不加）
	 */
	public void f1_3() {
		final TestObj obj1 = new TestObj();

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				obj1.method1();
			}
		}, "线程1");

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				obj1.method();
			}
		}, "线程2");

		t1.start();
		t2.start();
	}

	/**
	 * 两个线程执行同一个对象的同一个静态方法（方法加synchronized）
	 */
	public void f2() {
		final TestObj obj = new TestObj();

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				obj.method2();
			}
		}, "线程1");

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				obj.method2();
			}
		}, "线程2");

		t1.start();
		t2.start();
	}

	/**
	 * 两个线程执行两个对象的同一个静态方法（方法加synchronized）
	 */
	public void f2_1() {
		final TestObj obj1 = new TestObj();
		final TestObj obj2 = new TestObj();

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				obj1.method2();
			}
		}, "线程1");

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				obj2.method2();
			}
		}, "线程2");

		t1.start();
		t2.start();
	}

	/**
	 * 两个线程执行两个对象的两个静态方法（方法加synchronized）
	 */
	public void f2_2() {
		final TestObj obj1 = new TestObj();
		final TestObj obj2 = new TestObj();

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				obj1.method2();
			}
		}, "线程1");

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				obj2.method2_1();
			}
		}, "线程2");

		t1.start();
		t2.start();
	}

	/**
	 * 两个线程执行两个对象的两个方法（ 方法都加synchronized，一个静态方法，一个非静态）
	 */
	public void f2_3() {
		final TestObj obj1 = new TestObj();
		final TestObj obj2 = new TestObj();

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				obj1.method1();
			}
		}, "线程1");

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				obj2.method2();
			}
		}, "线程2");

		t1.start();
		t2.start();
	}

	/**
	 * 两个线程执行同一个对象的两个方法（ 方法都加synchronized，一个静态方法，一个非静态）
	 */
	public void f2_4() {
		final TestObj obj1 = new TestObj();

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				obj1.method1();
			}
		}, "线程1");

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				obj1.method2();
			}
		}, "线程2");

		t1.start();
		t2.start();
	}

	/**
	 * 两个线程执行同一个对象的同一个方法（方法中有synchronized代码块，且synchronized参数是对象属性）
	 */
	public void f3() {
		final TestObj obj = new TestObj();

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				obj.method3();
			}
		}, "线程1");

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				obj.method3();
			}
		}, "线程2");

		t1.start();
		t2.start();
	}

	/**
	 * 两个线程执行两个对象的同一个方法（方法中有synchronized代码块，且synchronized参数是对象属性）
	 */
	public void f3_1() {
		final TestObj obj1 = new TestObj();
		final TestObj obj2 = new TestObj();

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				obj1.method3();
			}
		}, "线程1");

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				obj2.method3();
			}
		}, "线程2");

		t1.start();
		t2.start();
	}

	/**
	 * 两个线程执行同一个对象的同一个方法（方法中有synchronized代码块，且synchronized参数是类属性）
	 */
	public void f4() {
		final TestObj obj = new TestObj();

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				obj.method4();
			}
		}, "线程1");

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				obj.method4();
			}
		}, "线程2");

		t1.start();
		t2.start();
	}

	/**
	 * 两个线程执行两个对象的同一个方法（方法中有synchronized代码块，且synchronized参数是类属性）
	 */
	public void f4_1() {
		final TestObj obj1 = new TestObj();
		final TestObj obj2 = new TestObj();

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				obj1.method4();
			}
		}, "线程1");

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				obj2.method4();
			}
		}, "线程2");

		t1.start();
		t2.start();
	}

	public void f5() {
		final TestObj obj = new TestObj();

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				obj.method5();
			}
		}, "线程1");

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				obj.method5();
			}
		}, "线程2");

		t1.start();
		t2.start();
	}

	public void f5_1() {
		final TestObj obj1 = new TestObj();
		final TestObj obj2 = new TestObj();

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				obj1.method5();
			}
		}, "线程1");

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				obj2.method5();
			}
		}, "线程2");

		t1.start();
		t2.start();
	}

	public void f5_2() {
		final TestObj obj1 = new TestObj();

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				obj1.method1();
			}
		}, "线程1");

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				obj1.method5();
			}
		}, "线程2");

		t1.start();
		t2.start();
	}

	public void f6() {
		final TestObj obj1 = new TestObj();

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				while(obj1.getNum()<100){
					obj1.setNum(obj1.getNum() + 1);
					System.out.println(Thread.currentThread().getName() + "obj1.getNum:" + obj1.getNum());
				}
			}
		}, "线程1");

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				while (obj1.getNum() < 100) {
					obj1.setNum(obj1.getNum() + 1);
					System.out.println(Thread.currentThread().getName() + "obj1.getNum:" + obj1.getNum());
				}
			}
		}, "线程2");

		t1.start();
		t2.start();
	}

}
