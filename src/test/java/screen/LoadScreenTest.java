package screen;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LoadScreenTest {

	private static LoadScreen l = new LoadScreen();
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testLoad() {
		l.load();
	}

}
