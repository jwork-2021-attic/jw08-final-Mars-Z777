package screen;

import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;
import java.io.IOException;

public interface Screen {

    public void displayOutput(AsciiPanel terminal);

    public Screen respondToUserInput(KeyEvent key);

	public abstract void update();
}
