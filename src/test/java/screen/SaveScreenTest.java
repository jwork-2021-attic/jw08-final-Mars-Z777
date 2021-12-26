package screen;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SaveScreenTest {

	private static SaveScreen s = new SaveScreen(new PlayScreen(""));
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSave() {
		s.save();
	}

}
