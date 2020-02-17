package com.jgc.study.jmx.mbean;

public interface HelloMBean {

	public String getName();

	public void setName(String name);

	public String printHello(String name);

	public String printHello();

}
