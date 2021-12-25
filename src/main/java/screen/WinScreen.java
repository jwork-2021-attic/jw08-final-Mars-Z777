package screen;

import asciiPanel.AsciiPanel;

public class WinScreen extends RestartScreen {

	@Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("You won!", 0, 0);
    }
    
    public void update() {
    	
    }

}
