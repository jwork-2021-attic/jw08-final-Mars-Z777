package screen;

import java.awt.event.KeyEvent;
import javax.swing.JFrame;

import asciiPanel.AsciiPanel;

public class StartScreen extends RestartScreen {

	public static JFrame jf;
	private int index;
	
	public StartScreen(JFrame f) {
		jf = f;
		index = 0;
	}
	
    @Override
    public void displayOutput(AsciiPanel terminal) {
    	terminal.write((char)16, 16, 12 + index * 4);
		terminal.write("SINGLE PLAYER", 18, 12);
		terminal.write("MULTI PLAYER", 18, 16);
		terminal.write("CONTINUE", 18, 20);
		terminal.write("EXIT", 18, 24);
    }
    
    @Override
	public Screen respondToUserInput(KeyEvent key) {
		switch(key.getKeyCode()) {
		case KeyEvent.VK_S:
			index = (index + 1) % 4;
			break;
		case KeyEvent.VK_W:
			index = (index - 1 + 4) % 4;
			break;
		case KeyEvent.VK_ENTER:
			if(index == 0) {
				return new SelectMapScreen();
			}
			else if(index == 1) {
				return new SelectServerScreen();
			}
			else if(index == 2) {
				return new LoadScreen();
			}
			else {
				System.exit(0);
			}
			break;
		}
		return this;
	}

    public void update() {
    	
    }

}
