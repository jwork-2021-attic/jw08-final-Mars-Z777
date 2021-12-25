package net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Random;

public class Server {

	private Selector selector;
	private InetSocketAddress address;
	private long mapSeed;
	
	public Server(String a, int p) {
		address = new InetSocketAddress(a, p);
		mapSeed = new Random().nextInt(100000);
	}
	
	public void start() throws IOException {
		selector = Selector.open();
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.bind(address);
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		while(true) {
			if(selector.select() == 0)
				continue;
			Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
			while(selectedKeys.hasNext()) {
				SelectionKey key = selectedKeys.next();
				if(key.isAcceptable()) {
					SocketChannel socketChannel = serverSocketChannel.accept();
					socketChannel.configureBlocking(false);
					socketChannel.register(selector, SelectionKey.OP_READ);
				}
				else if(key.isReadable()) {
					SocketChannel socketChannel = (SocketChannel)key.channel();
					ByteBuffer byteBuffer = ByteBuffer.allocate(128);
					int len = socketChannel.read(byteBuffer);
					if(len == -1) {
						socketChannel.close();
						key.cancel();
					}
					else {
						byte[] data = new byte[len];
						System.arraycopy(byteBuffer.array(), 0, data, 0, len);
						String s = new String(data);
						if(s.equals("seed?")){
							String ans = "seed " + mapSeed;
							byte[] seed = ans.getBytes();
							sendAll(seed);
						}
						else
							sendAll(data);
						byteBuffer.clear();
					}
				}
			}
		}
	}
	
	private void sendAll(byte[] data) throws IOException {
		Iterator<SelectionKey> keys = selector.keys().iterator();
		while(keys.hasNext()) {
			SelectionKey key = keys.next();
			SocketChannel channel = (SocketChannel)key.channel();
			ByteBuffer buffer = ByteBuffer.allocate(128);
			buffer.put(data);
			buffer.flip();
			channel.write(buffer);
			buffer.clear();
		}
	}
}
