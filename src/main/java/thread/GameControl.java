package thread;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import creature.Monster;
import creature.Player;
import screen.PlayScreen;

public class GameControl extends Thread {
	
	private Player player1, player2, player3;
	private ArrayList<Monster> monsters;
	private ExecutorService exec;
	
	public GameControl(PlayScreen screen) {
		player1 = screen.getPlayer(1);
		player2 = screen.getPlayer(2);
		player3 = screen.getPlayer(3);
		monsters = screen.getMonsters();
		exec = Executors.newCachedThreadPool();
	}
	
	public void start() {
		exec.execute(player1);
		if(player2 != null)
			exec.execute(player2);
		if(player3 != null)
			exec.execute(player3);
		for(Monster m : monsters)
			exec.execute(m);
	}
	
	public void addMonster(Monster m) {
		exec.execute(m);
	}
	
}
