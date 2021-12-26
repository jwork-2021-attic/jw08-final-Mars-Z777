import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ApplicationMainTest {

	private static ApplicationMain a = new ApplicationMain();
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testRepaint() {
		a.repaint();
	}

	@Test
	public void testApplicationMain() {
		ApplicationMain m = new ApplicationMain();
	}

}
