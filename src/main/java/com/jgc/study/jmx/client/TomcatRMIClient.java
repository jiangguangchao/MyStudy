package com.jgc.study.jmx.client;

import java.util.Iterator;
import java.util.Set;

import javax.management.MBeanServerConnection;
import javax.management.ObjectInstance;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class TomcatRMIClient {

	public static void main(String[] args) {

		try {
			int rmiPort = 9000;
			JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:" + rmiPort + "/" + "jmxrmi");
			JMXConnector jmxc = JMXConnectorFactory.connect(url);
			MBeanServerConnection connection = jmxc.getMBeanServerConnection();
			if (connection == null) {
				System.out.println("连接失败");
			} else {
				System.out.println("connection is not null");
			}

			System.out.println("all ObjectName:--------------");
			Set<ObjectInstance> set = connection.queryMBeans(null, null);
			for (Iterator<ObjectInstance> it = set.iterator(); it.hasNext();) {
				ObjectInstance oi = it.next();
				System.out.println("\t" + oi.getObjectName());
			}
			jmxc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
