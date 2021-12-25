package thread;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import net.Client;

public class Listener implements Runnable {

	private Client client;
	
	public Listener(Client c) {
		client = c;
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				TimeUnit.MILLISECONDS.sleep(50);
				client.read();
			}
		}catch(InterruptedException | IOException e) {
			System.out.println("ScreenUpdate thread error");
		}

	}

}
