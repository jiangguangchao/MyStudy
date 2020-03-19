package com.jgc.study.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @Description:NIO client端 
 * 参考https://www.cnblogs.com/snailclimb/p/9086334.html
 * 
 * @author jgc
 * 
 * @date:2020年2月17日 下午7:51:39
 */
public class NIOClient {
	// 通道管理器(Selector)
	private static Selector selector;

	public static void main(String[] args) throws IOException {
		// 创建通道管理器(Selector)
		selector = Selector.open();

		// 创建通道SocketChannel
		SocketChannel channel = SocketChannel.open();
		// 将通道设置为非阻塞
		channel.configureBlocking(false);

		/*
		 * 客户端连接服务器，其实方法执行并没有实现连接，需要在handleConnect方法中调
		 * channel.finishConnect()才能完成连接
		 * 因为是去连接服务器（实际上就是连接服务器上的某个channel），服务器就可以监听到
		 */
		channel.connect(new InetSocketAddress("localhost", 8989));

		/*
		 * 将channel注册到管理器(selector)中,并生成对应的key(SelectionKey对象)。
		 * channel注册过程可以这样理解，实际上是生成一个SelectionKey对象，SelectionKey
		 * 对象的一个属性为channel对象的引用。所以有方法key.channel()返回 SelectionKey
		 * 对象对应的channel。
		 * register(Selector sel,int ops)方法的第二个 参数是指定当前通道（channel）
		 * 关注的事件
		 * 
		 * 1. SelectionKey.OP_CONNECT:一般用于客户端，表示客户端将向指定的服务器发送连接请求
		 * 2. SelectionKey.OP_ACCEPT：一般用于服务端，表示服务器将接收客户端的连接请求。
		 * 3. SelectionKey.OP_READ: 表示读事件（如果有数据传输过来，会触发该事件，此时并未读取数据）
		 * 4. SelectionKey.OP_WRITE: 表示写事件（写数据时触发） 通常只关心前3个事件，写事件无需关心
		 */
		channel.register(selector, SelectionKey.OP_CONNECT);

		boolean f = false;
		// 循环处理
		while (f) {

			/*
			 * 
			 * 该方法可以传long类型的参数，如果不传，或者传0L,该方法会一直阻塞，直到有事件产生，
			 * 如果传参数，比如1000L，表示仅仅监听1000毫秒这个时间段产生的事件
			 * 
			 */
			selector.select();

			// 获取监听事件
			Set<SelectionKey> selectionKeys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = selectionKeys.iterator();

			// 迭代处理
			while (iterator.hasNext()) {
				// 获取事件
				SelectionKey key = iterator.next();

				// 移除事件，避免重复处理
				iterator.remove();

				// 检查是否是一个就绪的已经连接服务端成功事件
				if (key.isConnectable()) {
					handleConnect(key);
				} else if (key.isReadable()) {// 检查套接字是否已经准备好读数据
					handleRead(key);
				}

			}
		}
	}

	/**
	 * 处理客户端连接服务端成功事件
	 */
	private static void handleConnect(SelectionKey key) throws IOException {
		// 获取与服务端建立连接的通道
		SocketChannel channel = (SocketChannel) key.channel();
		if (channel.isConnectionPending()) {
			// channel.finishConnect()才能完成连接
			channel.finishConnect();
		}

		channel.configureBlocking(false);

		// 数据写入通道
		String msg = "Hello Server!";
		channel.write(ByteBuffer.wrap(msg.getBytes()));

		// 通道注册到选择器，并且这个通道只对读事件感兴趣
		channel.register(selector, SelectionKey.OP_READ);
	}

	/**
	 * 监听到读事件，读取客户端发送过来的消息
	 */
	private static void handleRead(SelectionKey key) throws IOException {
		SocketChannel channel = (SocketChannel) key.channel();

		// 从通道读取数据到缓冲区
		ByteBuffer buffer = ByteBuffer.allocate(128);
		channel.read(buffer);

		// 输出服务端响应发送过来的消息
		byte[] data = buffer.array();
		String msg = new String(data).trim();
		System.out.println("client received msg from server：" + msg);
	}
}