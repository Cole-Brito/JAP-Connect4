package connectfour.model;

import java.util.Timer;

public class GameManager {
	
	private GameBoard gameBoard = new GameBoard(); 
	private GameState gameState; 
	
	private Timer gameTimer = new Timer();
	private Timer turnTimer = new Timer();
	
	private PlayerManager playerManager = new PlayerManager();
	private Player player1 = new Player();
	private Player player2 = new Player();
	
	
	
	public void GamerManger() {
		
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
