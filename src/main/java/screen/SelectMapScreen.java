package screen;

import java.awt.FileDialog;
import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class SelectMapScreen implements Screen {

	private int index;
	
	public SelectMapScreen() {
		index = 0;
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		terminal.write((char)16, 16, 16 + index * 4);
		terminal.write("Create a new map", 18, 16);
		terminal.write("Select an existing map", 18, 20);
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
				return new PlayScreen("");
			}
			else {
				FileDialog fd = new FileDialog(StartScreen.jf, "╪сть", FileDialog.LOAD);
				fd.setVisible(true);
				String map = fd.getDirectory() + fd.getFile();
				return new PlayScreen(map);
			}
		}
		return this;
	}

	@Override
	public void update() {

	}

}
