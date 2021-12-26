package world;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

public class ThingTest {

	private static Thing t = new Thing(new Color(1, 2, 3), (char)2, null);
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetColor() {
		Color c = new Color(1, 2, 3);
		assertEquals(c, t.getColor());
	}

	@Test
	public void testGetGlyph() {
		assertEquals((char)2, t.getGlyph());
	}

}
