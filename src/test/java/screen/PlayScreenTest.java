package screen;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.Before;
import org.junit.Test;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;
import creature.Monster;
import creature.Player;
import world.Bullet;
import world.World;

public class PlayScreenTest {

	private static PlayScreen screen = new PlayScreen("");
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testWin() {
		screen.win();
		assertTrue(screen.getWin());
	}

	@Test
	public void testLose() {
		screen.lose();
		assertTrue(screen.getLose());
	}

	@Test
	public void testSetSeed() {
		screen.setSeed(100);
		assertEquals(100, screen.getSeed());
	}
	
	@Test
	public void testAction() {
		screen.action(1, 87);
		screen.action(1, 83);
		screen.action(1, 65);
		screen.action(1, 68);
		screen.action(1, 74);
	}
	
	@Test
	public void testDisplayOutput() {
		AsciiPanel terminal = new AsciiPanel(60, 40, AsciiFont.TALRYTH_15_15);
		screen.displayOutput(terminal);
	}
	
	@Test
	public void testStart() {
		try {
			screen.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testPlayScreen() {
		World w = new World("");
		Player p = new Player(new Color(255, 0, 0), w, null);
		w.put(p, 0, 0);
		ArrayList<Monster> monsters = new ArrayList<Monster>();
		CopyOnWriteArrayList<Bullet> bullets = new CopyOnWriteArrayList<Bullet>();
		PlayScreen l = new PlayScreen(w, p, monsters, bullets);
	}
	
	@Test
	public void testInit() {
		screen.init();
	}
}
