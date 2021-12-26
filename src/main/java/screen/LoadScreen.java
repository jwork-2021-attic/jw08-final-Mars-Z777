package screen;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import creature.Monster;
import creature.Player;
import world.Bullet;
import world.World;

public class LoadScreen extends DataScreen implements Screen {
	
	@Override
	public Screen respondToUserInput(KeyEvent key) {
		switch(key.getKeyCode()) {
		case KeyEvent.VK_S:
			index = (index + 1) % 3;
			break;
		case KeyEvent.VK_W:
			index = (index - 1 + 3) % 3;
			break;
		case KeyEvent.VK_ENTER:
			if(state[index]) {
				return load();
			}
		}
		return this;
	}
	
	public PlayScreen load() {
		World w = null;
		Player p = null;
		ArrayList<Monster> ms = new ArrayList<Monster>();
		CopyOnWriteArrayList<Bullet> bs = new CopyOnWriteArrayList<Bullet>();
		File f = new File(saves[index] + "/world.txt");
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
			w = (World)ois.readObject();
			ois.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		f = new File(saves[index] + "/player.txt");
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
			p = (Player)ois.readObject();
			ois.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		for(int i = 0; i < mon[index]; i++) {
			f = new File(saves[index] + "/monster" + i + ".txt");
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
				ms.add((Monster)ois.readObject());
				ois.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		for(int i = 0; i < mon[index]; i++) {
			f = new File(saves[index] + "/bullet" + i + ".txt");
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
				bs.add((Bullet)ois.readObject());
				ois.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return new PlayScreen(w, p, ms, bs);
	}
	
}
