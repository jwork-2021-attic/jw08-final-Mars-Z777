package maze;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MazeGeneratorTest {

	private static MazeGenerator m = new MazeGenerator(40, 1);
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testMazeGeneratorIntLong() {
		MazeGenerator m1 = new MazeGenerator(40, 1);
		assertEquals(m.getRawMaze(), m1.getRawMaze());
		assertEquals(m.getSymbolicMaze(), m1.getSymbolicMaze());
	}

}
