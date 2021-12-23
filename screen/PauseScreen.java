package screen;

import java.awt.FileDialog;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import asciiPanel.AsciiPanel;
import screen.Screen;
import world.World;

public class PauseScreen implements Screen {

	private int index;
	private PlayScreen parent;
	
	public PauseScreen(PlayScreen p) {
		index = 0;
		parent = p;
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		terminal.write((char)16, 20, 12 + index * 4);
		terminal.write("Save the map", 22, 12);
		terminal.write("Save the game", 22, 16);
		terminal.write("Back", 22, 20);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		switch(key.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
			return parent;
		case KeyEvent.VK_S:
			index = (index + 1) % 3;
			break;
		case KeyEvent.VK_W:
			index = (index - 1 + 3) % 3;
			break;
		case KeyEvent.VK_ENTER:
			if(index == 0) {
				FileDialog fd = new FileDialog(StartScreen.jf, "另存为", FileDialog.SAVE);
				fd.setVisible(true);
				int[][] map = World.Maze;
				String path = fd.getDirectory() + fd.getFile() + ".txt";
				File f = new File(path);
				if(!f.exists()) {
					try {
						f.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				String tmp = "";
				try {
					FileOutputStream out = new FileOutputStream(f);
					for(int i = 0; i < World.WIDTH; i++) {
						for(int j = 0; j < World.HEIGHT; j++) {
							tmp = map[i][j] + " ";
							out.write(tmp.getBytes());
						}
						tmp = "\n";
						out.write(tmp.getBytes());
					}
					out.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else if(index == 1) {
				return new SaveScreen(parent);
			}
			else {
				return parent;
			}
			break;
		}
		return this;
	}

	@Override
	public void update() {
		// TODO 自动生成的方法存根

	}

}