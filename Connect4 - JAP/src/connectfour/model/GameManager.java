package connectfour.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Timer;

public class GameManager {
	
	/** The singleton instance of GameManager */
	private static GameManager _instance;
	/**
	 * Gets the singleton instance of GameManager.
	 * Constructs a new GameManager only the first time it is called.
	 * @return The instance of GameManager
	 */
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
	
	private int player1WinCount;
	private int player2WinCount;
	
	/** PropertyChangeSupport for notifying PropertyChangeListeners of updated model */
	private final PropertyChangeSupport propertyChangedSupport;
	
	/**
	 * Private constructor for GameManager singleton.
	 * Sets initial states.
	 * Depends on PlayerManager being initialized.
	 */
	private GameManager() {
		var players = PlayerManager.getInstance().getPlayers();
		assert(players.size() >= 2);
		player1 = players.get(0);
		player2 = players.get(1);
		activePlayer = player1;
		gameState = GameState.PLAYER_1_TURN;
		propertyChangedSupport = new PropertyChangeSupport(this);
	}
	
	/**
	 * Attempts to place a tile in the given column.
	 * @param row Is ignored, as tiles will attempt to place in the first open row
	 * @param column The column to place the tile in
	 * @param activePlayer Which player is attempting to place a tile. Will only allow placement
	 * 		if the player is the same as the game's current activePlayer
	 * @return true if a tile was placed, false if denied for any reason (board full, wrong player, etc.)
	 */
	public boolean tryPlaceTile(int row, int column, Player activePlayer) {
		
		int currentPlayer = gameState.getPlayerID();
		// If no player is active, don't allow tile placement
		if (currentPlayer < 1) {
			return false;
		}
		
		if (gameBoard.getFirstEmptyRow(column) < 0) {
	        // Can't place on an occupied tile
	        return false;
	    }
		
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
			if (gameBoard.checkWinStates((short)placedRow, column, currentPlayer)) {
				updateGameState(GameState.PLAYER_1_WIN);
				++player1WinCount;
				onGameWinCountChanged();
				return true;
			}
		} 
		else if (gameState == GameState.PLAYER_2_TURN) {
			if (gameBoard.checkWinStates((short)placedRow, column, currentPlayer)) {
				updateGameState(GameState.PLAYER_2_WIN);
				++player2WinCount;
				onGameWinCountChanged();
				return true;
			}
		}
		
		if (gameBoard.isBoardFull()) {
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
		//TODO: implement this method
		return false;
	}
	/**
	 * loads the state of the game from a txt file 
	 * @param file
	 * @return
	 */
	public boolean loadGame(String file) {
		//TODO: implement this method
		return false;
	}
	
	/**
	 * Updates the game state. Notifies PropertyChangeListeners if the new state
	 * is different than the old state.
	 * @param state The new game state
	 */
	private void updateGameState(GameState state) {
		var oldState = gameState;
		if (state != gameState) {
			gameState = state;
			onGameStateChanged(gameState, oldState);
		}
	}
	
	/**
	 * Swaps active players between Player1 and Player2.
	 * Updates GameState.
	 */
	private void switchPlayers() {
		if (gameState == GameState.PLAYER_1_TURN) {
			activePlayer = player2;
			updateGameState(GameState.PLAYER_2_TURN);
		} 
		else if (gameState == GameState.PLAYER_2_TURN) {
			activePlayer = player1;
			updateGameState(GameState.PLAYER_1_TURN);
		}
	}
	
	/**
	 * Method to restart the game, setting all tiles to default state
	 * and setting player 1 as the active player
	 */
	public void restartGame() {
		for(int r = 0; r < 6; r++) {
			System.out.println("row changed");
			for(int c = 0; c < 7; c++) {
				System.out.println("column changed");
				gameBoard.setTileState(r, c, 0);
				onGameBoardChanged(r,c,0);
			}
		}
		updateGameState(GameState.PLAYER_1_TURN);
		
	}
	
	public int getPlayer1WinCount() {
		return player1WinCount;
	}
	
	public int getPlayer2WinCount() {
		return player2WinCount;
	}
	
	
	/********** PropertyChanged fields and methods **********/
	
	/** Property Name used to notify PropertyChange events when a tile on the game board changes */
	public static final String GAME_BOARD_TILE_PROPERTY_NAME = "GameBoardTile";
	/** Property Name used to notify PropertyChange events when the whole game board changes */
	public static final String GAME_BOARD_FULL_PROPERTY_NAME = "GameBoardFull";
	/** Property Name used to notify PropertyChange events when the game state changes */
	public static final String GAME_STATE_PROPERTY_NAME = "GameState";
	/** Property Name used to notify PropertyChange events when the players' win counts change */
	public static final String GAME_WIN_COUNT_PROPERTY_NAME = "GameWinCount";
	
	/**
	 * Registers a PropertyChangeListener to responds to changes to this model.
	 * Event will be fired upon changes to GameBoard, Players, GameState, etc.<br>
	 * 
	 * @param listener The {@link PropertyChangeListener} that responds to GameBoard being changed
	 */
	public void registerPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangedSupport.addPropertyChangeListener(propertyName, listener);
	}
	
	/**
	 * Notify PropertyChangeListeners when a GameBoard tile changes
	 * @param row The row index of the tile that changed
	 * @param column The column index of the tile that changed
	 * @param state The new state of the tile
	 */
	public void onGameBoardChanged(int row, int column, int state) {
		propertyChangedSupport.firePropertyChange(GAME_BOARD_TILE_PROPERTY_NAME, null, 
			new GameBoardPropertyChangedEvent(row, column, state));
	}
	
	/**
	 * Notify PropertyChangeListeners when the Game State changes
	 * @param newState The updated Game State
	 * @param oldState The previous Game State
	 */
	public void onGameStateChanged(GameState newState, GameState oldState) {
		propertyChangedSupport.firePropertyChange(GAME_STATE_PROPERTY_NAME, oldState, newState);
	}
	
	public void onGameWinCountChanged() {
		propertyChangedSupport.firePropertyChange(GAME_WIN_COUNT_PROPERTY_NAME, null,
			new GameWinCountChangedEvent(player1WinCount, player2WinCount));
	}
	
	/**
	 * Inner class used as oldValue and newValue in PropertyChange events for
	 * the {@value GameManager#GAME_BOARD_TILE_PROPERTY_NAME} property name.
	 */
	public class GameBoardPropertyChangedEvent{
		public final int row;
		public final int column;
		public final int state;
		
		/**
		 * Construct a new GameBoardPropertyChangedEvent
		 * @param row The row of the tile that updated
		 * @param column The column of the tile that updated
		 * @param state The new state of the tile
		 */
		public GameBoardPropertyChangedEvent(int row, int column, int state) {
			this.row = row;
			this.column = column;
			this.state = state;
		}
	}
	
	/**
	 * Inner class used as oldValue and newValue in PropertyChange events for
	 * the {@value GameManager#GAME_WIN_COUNT_PROPERTY_NAME} property name.
	 */
	public class GameWinCountChangedEvent{
		public final int player1WinCount;
		public final int player2WinCount;
		
		/**
		 * Construct a new GameWinCountChangedEvent
		 * @param p1Wins The player 1 win count
		 * @param p2Wins The player 2 win count
		 */
		public GameWinCountChangedEvent(int p1Wins, int p2Wins) {
			player1WinCount = p1Wins;
			player2WinCount = p2Wins;
		}
	}
}
