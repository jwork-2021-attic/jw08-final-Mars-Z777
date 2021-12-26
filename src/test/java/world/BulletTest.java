package world;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import creature.Monster;
import screen.PlayScreen;

public class BulletTest {

	private static Bullet b;
	
	@Before
	public void setUp() throws Exception {
		PlayScreen screen = new PlayScreen("");
		World w = screen.getWorld();
		b = new Bullet(new Color(0, 255, 0), w, screen, 1, new Monster(new Color(0, 0, 0), null, null));
		w.put(b, 1, 1);
	}

	@Test
	public void testBullet() {
		Bullet b1 = new Bullet(new Color(0, 255, 0), null, null, 1, new Monster(new Color(0, 0, 0), null, null));
		assertEquals(new Color(0, 255, 0), b1.getColor());
		assertEquals((char)7, b1.getGlyph());
	}

	@Test
	public void testMoveTo() {
		assertEquals(1, b.getX());
		assertEquals(1, b.getY());
		b.moveTo(1, 9);
		assertEquals(1, b.getX());
		assertEquals(9, b.getY());
	}

	@Test
	public void testSetScreen() {
		assertNotNull(b.getScreen());
		b.setScreen(null);
		assertNull(b.getScreen());
	}

	@Test
	public void testSetWorld() {
		assertNotNull(b.getWorld());
		b.setWorld(null);
		assertNull(b.getWorld());
	}

}
