package com.jgc.study.thread.synchroniz.base.test3;

/**
 * @Description:生产者消费者问题
 *
 * @author jgc
 *
 * @date:2020年2月21日 下午8:09:25
 */
public class TestMain {

	public static void main(String[] args) {
		Person person = new Person();
		Writer writer = new Writer(person);
		Reader reader = new Reader(person);
		Thread t1 = new Thread(writer, "生产者");
		Thread t2 = new Thread(reader, "消费者");
		t1.start();
		t2.start();
	}

}

/**
 * 
 * @Description:资源
 *
 * @author jgc
 *
 * @date:2020年2月21日 下午8:11:04
 */
class Person {

	public String name;
	public String sex;

	public boolean OpFlag = true;
}

/**
 * @Description:生产者，对资源进行写操作
 *
 * @author jgc
 *
 * @date:2020年2月21日 下午8:17:46
 */
class Writer extends Thread {

	public Person person = new Person();

	public Writer(Person person) {
		this.person = person;

	}

	@Override
	public void run() {
		boolean flag = true;

		while (true) {
			synchronized (person) {
				/*
				 * 添加wait和notify方法可以做到，每生产一个，就消费一个,
				 * 像这个例子，synchronized (person)，锁的是person对象，
				 * 调用wait和notify也必须是同一个person对象
				 */
				if (!person.OpFlag) {
					try {
						person.wait();
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (flag) {
					person.name = "小明";
					person.sex = "男";
				} else {
					person.name = "小红";
					person.sex = "女";
				}
				person.OpFlag = !person.OpFlag;
				person.notify();
			}

			flag = !flag;
		}
	}
}

/**
 * @Description:消费者，对资源进行写操作
 *
 * @author jgc
 *
 * @date:2020年2月21日 下午8:20:14
 */
class Reader extends Thread {
	public Person person = new Person();

	public Reader(Person person) {
		this.person = person;

	}

	@Override
	public void run() {
		while (true) {
			synchronized (person) {
				if (person.OpFlag) {
					try {
						person.wait();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				System.out.println("name:" + person.name + ", sex:" + person.sex);
				person.OpFlag = !person.OpFlag;
				person.notify();
			}
		}
	}
}

