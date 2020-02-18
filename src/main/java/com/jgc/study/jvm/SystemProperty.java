package com.jgc.study.jvm;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemProperty {

	private static final Logger log = LoggerFactory.getLogger(SystemProperty.class);

	public static void main(String[] args) {
		Properties pros = System.getProperties();
		if (pros == null) {
			log.info("pro == null");
			return;
		}

		log.info("pros.size: " + pros.size());
		for (String key : pros.stringPropertyNames()) {
			log.info("key:" + key + ", value:" + pros.getProperty(key));
		}
	}
}
