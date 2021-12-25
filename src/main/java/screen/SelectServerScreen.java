package screen;

import java.awt.FileDialog;
import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class SelectServerScreen implements Screen {

	private int index;
	
	public SelectServerScreen() {
		index = 0;
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		terminal.write((char)16, 16, 16 + index * 4);
		terminal.write("Create a new room", 18, 16);
		terminal.write("Join an existing room", 18, 20);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		switch(key.getKeyCode()) {
		case KeyEvent.VK_S:
			index = (index + 1) % 2;
			break;
		case KeyEvent.VK_W:
			index = (index - 1 + 2) % 2;
			break;
		case KeyEvent.VK_ENTER:
			if(index == 0) {
				return new ServerScreen();
			}
			else {
				return new SelectClientScreen();
			}
		}
		return this;
	}

	@Override
	public void update() {

	}

}
