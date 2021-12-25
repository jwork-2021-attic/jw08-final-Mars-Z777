package screen;

import asciiPanel.AsciiPanel;
import creature.*;
import net.*;
import thread.GameControl;
import thread.Listener;
import world.Bullet;
import world.Finish;
import world.Floor;
import world.Prop;
import world.World;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

public class PlayScreen implements Screen{

	private World world;
	private Player player;
	private Player player2 = null;
	private Player player3 = null;
	private Finish finish;
	private boolean win, lose;
	private ArrayList<Monster> monsters;
	private CopyOnWriteArrayList<Bullet> bullets;
	private CopyOnWriteArrayList<Bullet> deleteBullets;
	private CopyOnWriteArrayList<Prop> props;
	private CopyOnWriteArrayList<Prop> deleteProps;
	private GameControl controller;
	private int speedcount = 0;
	private int speed = 5;
	private int top;
	private int left;
	private int monsterNum;
	private boolean ispause;
	
	private int playerId = 1;
	private long mapSeed;
	private Client client;
	private boolean isMulti = false;
	private boolean isRunning = false;
    
    public PlayScreen(String map) {
    	win = false;
    	lose = false;
    	world = new World(map);
    	player = new Player(new Color(255, 0, 0), world, this);
    	world.put(player, 0, 0);
    	finish = new Finish(new Color(255, 0, 0), world);
    	world.put(finish, world.WIDTH - 1, world.HEIGHT - 1);
    	bullets = new CopyOnWriteArrayList<Bullet>();
    	deleteBullets = new CopyOnWriteArrayList<Bullet>();
    	props = new CopyOnWriteArrayList<Prop>();
    	deleteProps = new CopyOnWriteArrayList<Prop>();
    	monsters = new ArrayList<Monster>();
    	top = 6;
    	left = 43;
    	monsterNum = 15;
    	ispause = false;
    	
    	controller = new GameControl(this);
    	controller.start();
    	for(int i = 0; i < monsterNum; i++)
    		addMonster();
    }
    
    public PlayScreen(World w, Player p, ArrayList<Monster> ms, CopyOnWriteArrayList<Bullet> bs) {
    	win = false;
    	lose = false;
    	ispause = true;
    	world = w;
    	player = p;
    	p.setScreen(this);
    	p.setState(ispause);
    	p.setWorld(w);
    	world.put(player, p.getX(), p.getY());
    	finish = new Finish(new Color(255, 0, 0), world);
    	world.put(finish, world.WIDTH - 1, world.HEIGHT - 1);
    	bullets = bs;
    	for(int i = 0; i < bs.size(); i++) {
    		bs.get(i).setScreen(this);
    		bs.get(i).setWorld(w);
    	}
    	deleteBullets = new CopyOnWriteArrayList<Bullet>();
    	props = new CopyOnWriteArrayList<Prop>();
    	deleteProps = new CopyOnWriteArrayList<Prop>();
    	monsters = ms;
    	top = 6;
    	left = 43;
    	monsterNum = ms.size();
    	
    	controller = new GameControl(this);
    	controller.start();
    	for(int i = 0; i < monsterNum; i++) {
    		monsters.get(i).setScreen(this);
    		monsters.get(i).setState(ispause);
    		monsters.get(i).setWorld(w);
    		addMonster(monsters.get(i));
    	}
    }
    
    public PlayScreen(int id) throws IOException, InterruptedException{
    	isMulti = true;
    	playerId = id;
    	client = new Client(this, playerId);
    	client.start();
    	Listener lis = new Listener(client);
    	new Thread(lis).start();
    	world = new World(mapSeed);
    	player = new Player(new Color(255, 0, 0), world, this);
    	player2 = new Player(new Color(0, 0, 255), world, this);
    	player3 = new Player(new Color(255, 255, 0), world, this);
    	player.setState(true);
    	player2.setState(true);
    	player3.setState(true);
    	world.put(player, 0, 0);
    	world.put(player2, 0, world.HEIGHT - 1);
    	world.put(player3, world.WIDTH - 1, 0);
    	finish = new Finish(new Color(255, 0, 0), world);
    	// world.put(finish, world.WIDTH - 1, world.HEIGHT - 1);
    	bullets = new CopyOnWriteArrayList<Bullet>();
    	deleteBullets = new CopyOnWriteArrayList<Bullet>();
    	props = new CopyOnWriteArrayList<Prop>();
    	deleteProps = new CopyOnWriteArrayList<Prop>();
    	monsters = new ArrayList<Monster>();
    	top = 6;
    	left = 43;
    	monsterNum = 15;
    	ispause = false;
    	controller = new GameControl(this);
    }
    
    public void start() throws IOException {
    	if(!isRunning) {
    		controller.start();
    		// for(int i = 0; i < monsterNum; i++)
    		// 	addMonster();
    		player.setState(false);
    		player2.setState(false);
    		player3.setState(false);
    		isRunning = true;
    	}
    }
	
    private void messageUpdate(AsciiPanel terminal) {
    	String h = String.format("Hp: %2d/%2d", getPlayer(playerId).getHealth(), getPlayer(playerId).getMaxHp());
    	String p = String.format("Power: %d", getPlayer(playerId).getPower());
    	String ins = "Instructions:";
    	String w = "W: up";
    	String s = "S: down";
    	String a = "A: left";
    	String d = "D: right";
    	String j = "J: shoot";
    	String status = "Status: ";
    	terminal.write(h, left, top);
    	terminal.write(p, left, top + 2);
    	terminal.write(status, left, top + 8);
    	if(ispause)
    		terminal.write("Pause", left + 8, top + 8);
    	else
    		terminal.write("Running", left + 8, top + 8);
    	terminal.write(ins, left, top + 12);
    	terminal.write(w, left, top + 14);
    	terminal.write(s, left, top + 16);
    	terminal.write(a, left, top + 18);
    	terminal.write(d, left, top + 20);
    	terminal.write(j, left, top + 22);
    }
    
	@Override
	public void displayOutput(AsciiPanel terminal) {
		for (int x = 0; x < World.WIDTH; x++) {
            for (int y = 0; y < World.HEIGHT; y++) {

                terminal.write(world.get(x, y).getGlyph(), x, y, world.get(x, y).getColor());

            }
        }
		messageUpdate(terminal);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		if(isMulti) {
			if(multiWin())
				JOptionPane.showMessageDialog(null, "You are the winner!");
			try {
				client.action(key.getKeyCode());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return this;
		}
		if(win)
			return new WinScreen();
		else if(lose)
			return new LoseScreen();
		switch(key.getKeyCode()) {
		case KeyEvent.VK_W:
			player.move(0);
			break;
		case KeyEvent.VK_S:
			player.move(1);
			break;
		case KeyEvent.VK_A:
			player.move(2);
			break;
		case KeyEvent.VK_D:
			player.move(3);
			break;
		case KeyEvent.VK_J:
			player.attack();
			break;
		case KeyEvent.VK_ESCAPE:
			ispause = !ispause;
			player.setState(ispause);
			for(Monster m : monsters)
				m.setState(ispause);
			if(ispause)
				return new PauseScreen(this);
			break;
		}
		return this;
	}
	
	public void win() {
		win = true;
	}
	
    public void lose() {
    	lose = true;
    }
    
    public void addBullet(Bullet b) {
    	bullets.add(b);
    }
    
    public void deleteBullet(Bullet b) {
    	deleteBullets.add(b);
    }
    
    public void addProp(Prop p) {
    	props.add(p);
    }
    
    public void deleteProp(Prop p) {
    	deleteProps.add(p);
    }
    
    public void update() {
    	if(ispause)
    		return;
    	speedcount++;
    	if(speedcount == speed) {
    		for(Bullet b : bullets)
    			b.action();
    		speedcount = 0;
    	}
    	for(Bullet b: deleteBullets) {
    		bullets.remove(b);
    		int x = b.getX();
    		int y = b.getY();
    		world.put(new Floor(world), x, y);
    	}
    	deleteBullets.clear();
    	for(Prop p: deleteProps) {
    		props.remove(p);
    	}
    	deleteProps.clear();
    }
	
    public Player getPlayer() {
    	return player;
    }
    
    public World getWorld() {
    	return world;
    }
    
    public ArrayList<Monster> getMonsters(){
    	return monsters;
    }
    
    public CopyOnWriteArrayList<Bullet> getBullets(){
    	return bullets;
    }
    
    public void addMonster() {
    	Random r = new Random();
    	int x = r.nextInt(World.WIDTH);
    	int y = r.nextInt(World.HEIGHT);
    	while(world.posJudge(x, y) != 1) {
    		x = r.nextInt(World.WIDTH);
    		y = r.nextInt(World.HEIGHT);
    	}
    	Monster m = new Monster(new Color(0, 255, 0), world, this);
    	monsters.add(m);
    	world.put(m, x, y);
    	controller.addMonster(m);
    }
    
    public void addMonster(long seed) {
    	Random r = new Random(seed);
    	int x = r.nextInt(World.WIDTH);
    	int y = r.nextInt(World.HEIGHT);
    	while(world.posJudge(x, y) != 1) {
    		x = r.nextInt(World.WIDTH);
    		y = r.nextInt(World.HEIGHT);
    	}
    	Monster m = new Monster(new Color(0, 255, 0), world, this, seed);
    	monsters.add(m);
    	world.put(m, x, y);
    	controller.addMonster(m);
    }
    
    public void addMonster(Monster m) {
    	monsters.add(m);
    	world.put(m, m.getX(), m.getY());
    	controller.addMonster(m);
    }
    
    public void deleteMonster(Monster m) {
    	monsters.remove(m);
    }
    
    public int getMonNum() {
    	return monsters.size();
    }
    
    public int getBulletNum() {
    	return bullets.size();
    }
    
    public void setSeed(long l) {
    	mapSeed = l;
    }
    
    public Player getPlayer(int i) {
    	switch(i) {
    	case 1:
    		return player;
    	case 2:
    		return player2;
    	case 3:
    		return player3;
    	default:
    		return null;
    	}
    }
    
    public void action(int id, int code) {
    	Player p;
    	switch(id) {
    	case 1:
    		p = player;
    		break;
    	case 2:
    		p = player2;
    		break;
    	case 3:
    		p = player3;
    		break;
    	default:
    		p = null;
    		break;
    	}
    	switch(code) {
		case KeyEvent.VK_W:
			p.move(0);
			break;
		case KeyEvent.VK_S:
			p.move(1);
			break;
		case KeyEvent.VK_A:
			p.move(2);
			break;
		case KeyEvent.VK_D:
			p.move(3);
			break;
		case KeyEvent.VK_J:
			p.attack();
			break;
    	}
    }
    
    private boolean multiWin() {
    	Player p = getPlayer(playerId);
    	Player x = getPlayer(playerId % 3 + 1);
    	Player y = getPlayer((playerId + 1) % 3 + 1);
    	if(p.getHealth() > 0 && x.getHealth() <= 0 && y.getHealth() <= 0)
    		return true;
    	return false;
    }
}
