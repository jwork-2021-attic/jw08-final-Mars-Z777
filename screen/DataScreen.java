package screen;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.concurrent.CopyOnWriteArrayList;

import asciiPanel.AsciiPanel;
import world.Bullet;

public class DataScreen implements Screen {

	protected int index;
	protected String[] saves;
	protected boolean[] state;
	protected String[] dat;
	protected int[] mon, bn;
	
	public DataScreen() {
		index = 0;
		saves = new String[3];
		saves[0] = "saves/1";
		saves[1] = "saves/2";
		saves[2] = "saves/3";
		state = new boolean[3];
		dat = new String[3];
		mon = new int[3];
		bn = new int[3];
		for(int i = 0; i < 3; i++) {
			dat[i] = "";
			mon[i] = 0;
			bn[i] = 0;
			File f = new File(saves[i]);
			File[] lst = f.listFiles();
			if(lst.length == 0)
				state[i] = false;
			else {
				state[i] = true;
				String info = saves[i] + "/info.txt";
				try {
					BufferedReader in = new BufferedReader(new FileReader(info));
					String tmp = "";
					tmp = in.readLine();
					dat[i] = tmp;
					tmp = in.readLine();
					mon[i] = Integer.valueOf(tmp);
					tmp = in.readLine();
					bn[i] = Integer.valueOf(tmp);
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		terminal.write((char)16, 16, 16 + index * 4);
		terminal.write("Select the save", 18, 12);
		terminal.write("Save 1: ", 18, 16);
		terminal.write("Save 2: ", 18, 20);
		terminal.write("Save 3: ", 18, 24);
		for(int i = 0; i < 3; i++) {
			if(!state[i])
				terminal.write("Empty", 26, 16 + i * 4);
			else
				terminal.write(dat[i], 26, 16 + i * 4);
		}
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		return this;
	}

	@Override
	public void update() {

	}

}
