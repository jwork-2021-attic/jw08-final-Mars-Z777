package creature;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

public class MonsterTest {

	private static Monster m = new Monster(new Color(0, 0, 255), null, null, 10);
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testMonsterColorWorldPlayScreen() {
		Monster m1 = new Monster(new Color(0, 0, 255), null, null);
		assertEquals(new Color(0, 0, 255), m1.getColor());
		assertEquals((char)1, m1.getGlyph());
		assertEquals(5, m.getHealth());
		assertEquals(5, m.getMaxHp());
	}

	@Test
	public void testMonsterColorWorldPlayScreenLong() {
		Monster m1 = new Monster(new Color(0, 0, 255), null, null, 10);
		assertEquals(m.getPower(), m1.getPower());
	}

}
