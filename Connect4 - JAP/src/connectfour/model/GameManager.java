package connectfour.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Timer;

import connectfour.model.ChatManager.MessageEventValue;

public class GameManager {
	
	private static GameManager _instance;
	public static GameManager getInstance() {
		if (_instance == null) {
			_instance = new GameManager();
		}
		return _instance;
	}
	
	private GameBoard gameBoard = new GameBoard(); 
	private GameState gameState; 
	
	private Timer gameTimer = new Timer();
	private Timer turnTimer = new Timer();
	
	private Player player1;
	private Player player2;
	public Player activePlayer;
	
	private final PropertyChangeSupport propertyChangedSupport;
	
	private GameManager() {
		var players = PlayerManager.getInstance().getPlayers();
		assert(players.size() >= 2);
		player1 = players.get(0);
		player2 = players.get(1);
		activePlayer = player1;
		gameState = GameState.PLAYER_1_TURN;
		propertyChangedSupport = new PropertyChangeSupport(this);
	}
	
	public boolean tryPlaceTile(short row, short column, Player activePlayer) {
		if (gameBoard.getFirstEmptyRow(column) < 0) {
	        // Cant place on an occupied tile
	        return false;
	    }
		
		short currentPlayer = gameState == GameState.PLAYER_1_TURN ? (short)1 : (short)2;
		
		int placedRow = -1;
		//Setting the state of the tile based on the active player
		//Could probably condense most of this with ternary operators
		if (gameState == GameState.PLAYER_1_TURN) {
			placedRow = gameBoard.setTileInColumn(column, currentPlayer);
			if (placedRow < 0) {
				return false;
			}
		} 
		else if (gameState == GameState.PLAYER_2_TURN) {
			placedRow = gameBoard.setTileInColumn(column, currentPlayer);
			if (placedRow < 0) {
				return false;
			}
		}
		else {
			return false;
		}
		
		onGameBoardChanged(placedRow, column, currentPlayer);
		
		if (gameState == GameState.PLAYER_1_TURN) {
			System.out.println("Check player 1");
			if (gameBoard.checkWinStates((short)placedRow, column, currentPlayer)) {
				updateGameState(GameState.PLAYER_1_WIN);
				System.out.println(gameState.toString());
				return true;
			}
		} 
		else if (gameState == GameState.PLAYER_2_TURN) {
			System.out.println("Check player 2");
			if (gameBoard.checkWinStates((short)placedRow, column, currentPlayer)) {
				updateGameState(GameState.PLAYER_2_WIN);
				System.out.println(gameState);
				return true;
			}
		}
		else if (gameBoard.isBoardFull()) {
			updateGameState(GameState.DRAW);
			return true;
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
	
	
	
	/**
	 * Registers a PropertyChangeListener to responds to changes to GameBoard.
	 * Event will be fired when a new message is added.<br>
	 * Note that the PropertyChangeEvent will use {@link } as old and new values.
	 * 
	 * @param listener The {@link PropertyChangeListener} that responds to GameBoard being changed
	 */
	public void registerGameBoardPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangedSupport.addPropertyChangeListener("gameBoard", listener);
	}
	
	public void onGameBoardChanged(int row, int column, int state) {
		propertyChangedSupport.firePropertyChange("gameBoard", null, 
			new GameBoardPropertyChangedEvent(row, column, state));
	}
	
	public class GameBoardPropertyChangedEvent{
		public final int row;
		public final int column;
		public final int state;
		
		public GameBoardPropertyChangedEvent(int row, int column, int state) {
			this.row = row;
			this.column = column;
			this.state = state;
		}
	}
}
