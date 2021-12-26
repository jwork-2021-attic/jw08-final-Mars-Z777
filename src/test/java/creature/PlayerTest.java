package creature;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import screen.PlayScreen;

public class PlayerTest {
	
	private static Player p;

	@Before
	public void setUp() throws Exception {
		PlayScreen screen = new PlayScreen("");
		p = screen.getPlayer();
	}

	@Test
	public void testMoveTo() {
		p.moveTo(10, 10);
		assertEquals(p, (Player)p.getWorld().get(10, 10));
	}

	@Test
	public void testBeHit() {
		p.beHit(4);
		assertEquals(1, p.getHealth());
		p.beHit(2);
		assertEquals(0, p.getHealth());
	}

	@Test
	public void testGetMaxHp() {
		int m = p.getMaxHp();
		assertEquals(5, m);
	}

	@Test
	public void testSetState() {
		assertFalse(p.getS());
		p.setState(true);
		assertTrue(p.getS());
	}

	@Test
	public void testSetScreen() {
		assertNotNull(p.getScreen());
		p.setScreen(null);
		assertNull(p.getScreen());
	}

	@Test
	public void testSetWorld() {
		assertNotNull(p.getWorld());
		p.setWorld(null);
		assertNull(p.getWorld());
	}

}
