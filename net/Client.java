package net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

import screen.PlayScreen;

public class Client {

	private PlayScreen screen;
	private int id;
	private long mapSeed;
	private ByteBuffer buffer;
    private InetSocketAddress serverAddress;
    private SocketChannel channel;
	
	public Client(PlayScreen s, int i) throws IOException {
		screen = s;
		id = i;
		mapSeed = -1;
		buffer = ByteBuffer.allocate(128);
		serverAddress = new InetSocketAddress("localhost", 9093);
		channel = SocketChannel.open(serverAddress);
	}
	
	public void write(String s) throws IOException {
		byte[] data = s.getBytes();
		buffer.put(data);
		buffer.flip();
		channel.write(buffer);
		buffer.clear();
		mapSeed = -1;
	}
	
	public void read() throws IOException {
		int len = channel.read(buffer);
		if(len > 0) {
			byte[] data = new byte[len];
			System.arraycopy(buffer, 0, data, 0, len);
			String s = new String(data);
			handle(s);
			buffer.clear();
		}
	}
	
	private void handle(String s) {
		String[] tmp = s.split(" ");
		if(tmp[0].equals("seed")) {
			mapSeed = Long.valueOf(tmp[1]);
			screen.setSeed(mapSeed);
		}
		else if(tmp[0].equals("action")) {
			int playerId = Integer.valueOf(tmp[1]);
			int code = Integer.valueOf(tmp[2]);
			if(code == 10)
				screen.start();
			else
				screen.action(playerId, code);
		}
	}
	
	public void start() throws InterruptedException, IOException {
		while(mapSeed == -1) {
			TimeUnit.MILLISECONDS.sleep(100);
			write("seed?");
			read();
		}
	}
	
	public long getSeed() {
		return mapSeed;
	}
	
	public void action(int code) throws IOException {
		String data = "action " + id + " " + code;
		write(data);
	}
	
}
