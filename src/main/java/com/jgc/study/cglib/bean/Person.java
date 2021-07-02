package com.jgc.study.cglib.bean;

/**
 * @program: MyStudy
 * @description:
 * @author:
 * @create: 2021-06-30 08:28
 */
public class Person {

    private String name;

    public void work(String jobName) {
        System.out.println(name + "正在做[" + jobName + "]工作。。。");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        System.out.println("名字设为：" + name);
    }
}
