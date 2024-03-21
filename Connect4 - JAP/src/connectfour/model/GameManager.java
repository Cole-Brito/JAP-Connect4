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
	public Player activePlayer;
	
	public void GamerManger() {
		Player activePlayer = player1;//temp for testing local
		this.gameState = GameState.PLAYER_1_TURN;
	}
	
	public boolean tryPlaceTile(short row, short column, Player activePlayer) {
		if (activePlayer == player1) {
			for (short r = GameBoard.NUM_ROWS - 1; r >=0; r--) {
				gameBoard.setTileState(row, column, (short) 1);
			}
		} else {
			for (short r = GameBoard.NUM_ROWS - 1; r >=0; r--) {
				gameBoard.setTileState(row, column, (short) 2);
		}
		
		if (gameBoard.checkWinStates(row, column, activePlayer)) {
			
		}
		
		switchPlayers();
		return true;
	}
	
	/**
	 * Saves the state of the game as a txt file 
	 * @param file
	 * @return
	 */
	public boolean saveGame(String file) {
		
		return false;
	}
	/**
	 * loads the state of the game from a txt file 
	 * @param file
	 * @return
	 */
	public boolean loadGame(String file) {
		
		return false;
	}
	
	private void updateGameState(GameState state) {
		this.gameState = state;
	}
	
//	public void propertyChanged() {
//		
//	}
	
	private void switchPlayers() {
		if (activePlayer == player1) {
			activePlayer = player2;
			updateGameState(GameState.PLAYER_2_TURN);
		} else {
			activePlayer = player1;
			updateGameState(GameState.PLAYER_1_TURN);
		}
	}
}
