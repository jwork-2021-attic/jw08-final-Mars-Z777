package world;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TileTest {

	private static Tile t = new Tile();
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSetxPos() {
		t.setxPos(10);
		assertEquals(10, t.getxPos());
		t.setxPos(50);
		assertEquals(50, t.getxPos());
	}

	@Test
	public void testSetyPos() {
		t.setyPos(1);
		assertEquals(1, t.getyPos());
		t.setyPos(-1);
		assertEquals(-1, t.getyPos());
	}

	@Test
	public void testTile() {
		Tile tt = new Tile();
		assertEquals(-1, tt.getxPos());
		assertEquals(-1, tt.getyPos());
	}

}
