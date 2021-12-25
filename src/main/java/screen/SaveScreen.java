package screen;

import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

import creature.Monster;
import world.Bullet;

public class SaveScreen extends DataScreen implements Screen {

	private PlayScreen p;
	
	public SaveScreen(PlayScreen play) {
		p = play;
	}
	
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
			Date d = new Date();
			SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm");
			dat[index] = ft.format(d);
			mon[index] = p.getMonNum();
			bn[index] = p.getBulletNum();
			File f = new File(saves[index] + "/info.txt");
			try {
				if(!f.exists())
					f.createNewFile();
				BufferedWriter in = new BufferedWriter(new FileWriter(f));
				in.write(dat[index] + "\n");
				in.write(String.valueOf(mon[index]) + "\n");
				in.write(bn[index] + "\n");
				in.flush();
				in.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
			f = new File(saves[index] + "/world.txt");
			try {
				if(!f.exists())
					f.createNewFile();
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
				oos.writeObject(p.getWorld());
				oos.flush();
				oos.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
			f = new File(saves[index] + "/player.txt");
			try {
				if(!f.exists())
					f.createNewFile();
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
				oos.writeObject(p.getPlayer());
				oos.flush();
				oos.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
			ArrayList<Monster> ms = p.getMonsters();
			for(int i = 0; i < mon[index]; i++) {
				f = new File(saves[index] + "/monster" + i + ".txt");
				try {
					if(!f.exists())
						f.createNewFile();
					ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
					oos.writeObject(ms.get(i));
					oos.flush();
					oos.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			CopyOnWriteArrayList<Bullet> bs = p.getBullets();
			for(int i = 0; i < bn[index]; i++) {
				f = new File(saves[index] + "/bullet" + i + ".txt");
				try {
					if(!f.exists())
						f.createNewFile();
					ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
					oos.writeObject(bs.get(i));
					oos.flush();
					oos.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			return p;
		}
		return this;
	}
	
}
