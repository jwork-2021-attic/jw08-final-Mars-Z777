package screen;

import asciiPanel.AsciiPanel;

public class LoseScreen extends RestartScreen {

	@Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("You lost!", 0, 0);
    }
    
    public void update() {
    	
    }

}
