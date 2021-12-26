package screen;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;

public class DataScreenTest {

	private static DataScreen screen = new DataScreen();
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testDataScreen() {
		DataScreen s = new DataScreen();
	}

	@Test
	public void testDisplayOutput() {
		AsciiPanel terminal = new AsciiPanel(60, 40, AsciiFont.TALRYTH_15_15);
		screen.displayOutput(terminal);
	}

}
