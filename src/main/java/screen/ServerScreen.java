package screen;

import java.awt.event.KeyEvent;
import java.io.IOException;

import asciiPanel.AsciiPanel;
import net.Server;

public class ServerScreen implements Screen {

	private Server server;
	
	public ServerScreen() {
		server = new Server("localhost", 7890);
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		terminal.write("Press any key to start the server", 10, 16);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		try {
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	@Override
	public void update() {

	}

}
