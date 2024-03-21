package connectfour.model;

import java.util.Timer;

public class GameManager {
	
	private GameBoard gameBoard = new GameBoard(); 
	private GameState gameState; 
	
	private Timer gameTimer = new Timer();
	private Timer turnTimer = new Timer();
	
	private Player player1;
	private Player player2;
	
	
	public GameManager() {
		var players = PlayerManager.getInstance().getPlayers();
		assert(players.size() >= 2);
		player1 = players.get(0);
		player2 = players.get(1);
	}
	
	public boolean tryPlaceTile(int column, short player) {
		
		return false;
	}
	
	public boolean saveGame(String file) {
		
		return false;
	}
	
	public boolean loadGame(String file) {
		
		return false;
	}
	
	private void updateGameState() {
		
	}
	
	private void switchPlayers() {
		
	}
}
