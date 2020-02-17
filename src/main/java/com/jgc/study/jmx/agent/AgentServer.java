package com.jgc.study.jmx.agent;

import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

import com.jgc.study.jmx.mbean.Hello;

public class AgentServer {

	public static void main(String[] args) {

		try {
			MBeanServer server = ManagementFactory.getPlatformMBeanServer();

			String domainName = "MyMBean";
			ObjectName objectName = new ObjectName(domainName + ":name=HelloWorld");
			final Hello hello = new Hello();
			server.registerMBean(hello, objectName);

			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						Thread.sleep(20000);
						System.out.println("线程休眠结束");

						int i = 0;
						while (i < 10) {
							hello.setName("jgc" + i);
							System.out.println("hello.name:" + hello.getName());
							Thread.sleep(5000);
							i++;
						}
					} catch (Exception e) {
						System.out.println("线程异常");
					}

				}

			}).start();

			int rmiPort = 9000;
			Registry registry = LocateRegistry.createRegistry(rmiPort);

			JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:" + rmiPort + "/"
					+ domainName);
			JMXConnectorServer jmxConnector = JMXConnectorServerFactory.newJMXConnectorServer(url, null, server);
			jmxConnector.start();
			System.out.println("服务端启动正常");

		} catch (Exception e) {
			System.out.println("服务端异常");
			e.printStackTrace();
		}

	}
}
