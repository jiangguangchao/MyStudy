package com.jgc.study.jmx.client;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import com.jgc.study.jmx.mbean.HelloMBean;

public class HelloMBeanClient {

	public static void main(String[] args) {

		try {
			String domainName = "MyMBean";
			int rmiPort = 9000;
			JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:" + rmiPort + "/"
					+ domainName);
			JMXConnector jmxc = JMXConnectorFactory.connect(url);
			MBeanServerConnection connection = jmxc.getMBeanServerConnection();

			ObjectName mBeanName = new ObjectName(domainName + ":name=HelloWorld");
			// connection.setAttribute(mBeanName, new Attribute("Name",
			// "zzh"));// 注意这里是Name而不是name

			HelloMBean proxy = MBeanServerInvocationHandler.newProxyInstance(connection, mBeanName, HelloMBean.class,
					true);

			System.out.println("name:" + proxy.getName());
			connection.getAttribute(mBeanName, "Name");

			Object result = connection.invoke(mBeanName, "printHello", null, null);
			if (result != null && result instanceof String) {
				System.out.println("via rmi get name:" + result);
			}

		} catch (Exception e) {
			System.out.println("客户端异常");
			e.printStackTrace();
		}
	}

}
