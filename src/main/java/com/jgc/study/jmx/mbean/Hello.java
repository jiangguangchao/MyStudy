package com.jgc.study.jmx.mbean;

public class Hello implements HelloMBean {

	private String name;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String printHello(String name) {

		return this.name + "say hello to " + name;
	}

	@Override
	public String printHello() {
		return name;
	}

}
