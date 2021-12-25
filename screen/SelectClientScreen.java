package screen;

import java.awt.event.KeyEvent;
import java.io.IOException;

import asciiPanel.AsciiPanel;

public class SelectClientScreen implements Screen {

	private int index;
	
	public SelectClientScreen() {
		index = 0;
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		terminal.write((char)16, 16, 16 + index * 4);
		terminal.write("Select the client", 18, 12);
		terminal.write("Player 1", 18, 16);
		terminal.write("Player 2", 18, 20);
		terminal.write("Player 3", 18, 24);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		switch(key.getKeyCode()) {
		case KeyEvent.VK_S:
			index = (index + 1) % 3;
			break;
		case KeyEvent.VK_W:
			index = (index - 1 + 3) % 3;
			break;
		case KeyEvent.VK_ENTER:
			try {
				return new PlayScreen(index + 1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		return this;
	}

	@Override
	public void update() {

	}

}
