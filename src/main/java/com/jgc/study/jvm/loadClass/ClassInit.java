package com.jgc.study.jvm.loadClass;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description:测试jvm加载类的情况
 * @author:jgc
 * @create:2020-03-25 11:25
 */
public class ClassInit {

    private static final Logger log = LoggerFactory.getLogger(ClassInit.class);

    private ClassInit() {
        log.info("这是类的无参构造方法");
    }

    public static void main(String[] args) {
        log.info("这是main方法--");
    }
}
