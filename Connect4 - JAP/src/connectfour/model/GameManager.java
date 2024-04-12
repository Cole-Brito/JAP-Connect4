/**
 * Connect4
 * Authors: Cole Brito, Paul Squires 
 * Section: 301
 * Professor: Daniel Cormier
 * Last Modified: April 6, 2024
 * Algonquin College CET-CS
 * JAP - Assignment 3-2
 */

package connectfour.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import connectfour.model.timer.ControllableTimer;
import connectfour.model.timer.ControllableTimerChangeListener;

/**
 * Handles the current GameBoard and win states.
 * Validates tile placements and active players.
 */
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
	
	/** The GameBoard that this class manages */
	private GameBoard gameBoard = new GameBoard();
	/** The current GameState, such as player turns or win states */
	private GameState gameState; 
	
	/** ControllableTimer to track game time */
	private ControllableTimer gameTimer = new ControllableTimer(null);
	/** ControllableTimer to track turn time */
	private ControllableTimer turnTimer = new ControllableTimer(null);
	
	/** The current player 1 for this game */
	private Player player1;
	/** The current player 2 for this game */
	private Player player2;
	
	/** The total player 1 wins this session */
	private int player1WinCount;
	/** The total player 2 wins this session */
	private int player2WinCount;
	
	/** PropertyChangeSupport for notifying PropertyChangeListeners of updated model */
	private final PropertyChangeSupport propertyChangedSupport;
	
	/**
	 * Private constructor for GameManager singleton.
	 * Sets initial states.
	 * Depends on PlayerManager being initialized.
	 */
	private GameManager() {
		var playerManager = PlayerManager.getInstance();
		player1 = playerManager.getLocalPlayer1();
		player2 = playerManager.getLocalPlayer2();
		gameState = GameState.PLAYER_1_TURN;
		propertyChangedSupport = new PropertyChangeSupport(this);
		//gameTimer.setStatus(ControllableTimer.START);
		//turnTimer.setStatus(ControllableTimer.START);
	}
	
	/**
	 * Attempts to place a tile in the given column.
	 * @param column The column to place the tile in
	 * @param activePlayer Which player is attempting to place a tile. Will only allow placement
	 * 		if the player is the same as the game's current activePlayer
	 * @return true if a tile was placed, false if denied for any reason (board full, wrong player, etc.)
	 */
	public boolean tryPlaceTile(int column, Player activePlayer) {
		
		int currentPlayer = gameState.getPlayerID();
		// If no player is active or wrong player is placing, don't allow tile placement
		if (currentPlayer < 1 || activePlayer == null || !activePlayer.equals(getActivePlayer())) {
			return false;
		}
		
		if (gameBoard.getFirstEmptyRow(column) < 0) {
	        // Can't place on an occupied tile
	        return false;
	    }
		
		int placedRow = -1;
		//Setting the state of the tile based on the active player
		if (gameState == GameState.PLAYER_1_TURN || gameState == GameState.PLAYER_2_TURN) {
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
			if (gameBoard.checkWinStates(placedRow, column, currentPlayer)) {
				updateGameState(GameState.PLAYER_1_WIN);
				setPlayer1WinCount(player1WinCount + 1);
				return true;
			}
		} 
		else if (gameState == GameState.PLAYER_2_TURN) {
			if (gameBoard.checkWinStates(placedRow, column, currentPlayer)) {
				updateGameState(GameState.PLAYER_2_WIN);
				setPlayer2WinCount(player2WinCount + 1);
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
	 * Updates a tile on the game board without checking win states.
	 * Typically done as a result of server updates on client.
	 * @param row The row index of the tile
	 * @param column The column index of the tile
	 * @param state The new tile state
	 */
	public void updateGameTileDirect(int row, int column, int state) {
		gameBoard.setTileState(row, column, state);
		onGameBoardChanged(row, column, state);
	}
	
	/**
	 * Updates the entire gameboard without checking win states.
	 * Typically done as a result of server updates on client.
	 * @param tiles 2D array of tile states
	 */
	public void updateGameBoardDirect(int[][] tiles) {
		if (tiles != null) {
			gameBoard.setBoardState(tiles);
			onGameBoardReset();			
		}
	}
		
	
	/**
	 * Saves the state of the game as a txt file 
	 * @param file The filename to read
	 * @return false TODO:
	 */
	public boolean saveGame(String file) {
		//TODO: implement this method
		return false;
	}
	/**
	 * loads the state of the game from a txt file 
	 * @param file The filename to read
	 * @return false TODO:
	 */
	public boolean loadGame(String file) {
		//TODO: implement this method
		return false;
	}
	
	/**
	 * Gets the state of game board tiles
	 * @return 2D array of tile states
	 */
	public int[][] getBoardState(){
		return gameBoard.getBoardState();
	}
	
	/**
	 * Gets the current game state
	 * @return gameState
	 */
	public GameState getGameState() {
		return this.gameState;
	}
	
	/**
	 * Updates the game state. Notifies PropertyChangeListeners if the new state
	 * is different than the old state.
	 * @param state The new game state
	 */
	public void updateGameState(GameState state) {
		var oldState = gameState;
		if (state != gameState) {
			gameState = state;
			onGameStateChanged(gameState, oldState);
			if (gameState == GameState.PLAYER_1_TURN || gameState == GameState.PLAYER_2_TURN) {
				if (oldState == GameState.PLAYER_1_TURN || oldState == GameState.PLAYER_2_TURN) {
					turnTimer.setStatus(ControllableTimer.RESET);
				}
				else {
					gameTimer.setStatus(ControllableTimer.START);
					turnTimer.setStatus(ControllableTimer.START);					
				}
			}
			else {
				gameTimer.setStatus(ControllableTimer.STOP);
				turnTimer.setStatus(ControllableTimer.STOP);
			}
		}
	}
	
	/**
	 * Updates the game state from integer ordinal. Notifies PropertyChangeListeners if the new state
	 * is different than the old state.
	 * @param gameStateOrdinal The integer value of the new game state
	 */
	public void updateGameState(Integer gameStateOrdinal) {
		if (gameStateOrdinal == null || gameStateOrdinal < 0 || gameStateOrdinal >= GameState.values().length) {
			return;
		}
		var newState = GameState.values()[gameStateOrdinal];
		updateGameState(newState);
	}
	
	/**
	 * Swaps active players between Player1 and Player2.
	 * Updates GameState.
	 */
	private void switchPlayers() {
		if (gameState == GameState.PLAYER_1_TURN) {
			updateGameState(GameState.PLAYER_2_TURN);
			turnTimer.setStatus(ControllableTimer.RESET);
		} 
		else if (gameState == GameState.PLAYER_2_TURN) {
			updateGameState(GameState.PLAYER_1_TURN);
			turnTimer.setStatus(ControllableTimer.RESET);
		}
	}
	
	/**
	 * Tries to start the game if game is not active.
	 * Does not reset board state
	 */
	public void startGame() {
		if (player1 != null && player2 != null && 
				gameState != GameState.PLAYER_1_TURN && gameState != GameState.PLAYER_2_TURN) {
			updateGameState(GameState.PLAYER_1_TURN);
		}
	}
	
	/**
	 * Method to restart the game, setting all tiles to default state
	 * and setting player 1 as the active player.
	 */
	public void restartGame() {
		gameBoard.resetBoardState();
		onGameBoardReset();
		gameTimer.setStatus(ControllableTimer.RESET);
		turnTimer.setStatus(ControllableTimer.RESET);
		if (player1 != null && player2 != null) {
			updateGameState(GameState.PLAYER_1_TURN);
		}
		else {
			updateGameState(GameState.DEFAULT);
			gameTimer.setStatus(ControllableTimer.STOP);
			turnTimer.setStatus(ControllableTimer.STOP);
		}
	}
	
	/**
	 * Sets the default local players and resets the game
	 */
	public void resetToLocalGameState() {
		this.setPlayer1(PlayerManager.getInstance().getLocalPlayer1());
		this.setPlayer2(PlayerManager.getInstance().getLocalPlayer2());
		this.restartGame();
		updateGameState(GameState.DEFAULT);
	}
	
	/**
	 * Unsets player 2 and resets the game
	 * @param hosting If this is the host, player 1 is set to default
	 */
	public void resetToNetworkGameState(boolean hosting) {
		updateGameState(GameState.DEFAULT);
		if (hosting) {
			this.setPlayer1(PlayerManager.getInstance().getLocalPlayer1());			
		}
		else {
			this.setPlayer1(null);
		}
		this.setPlayer2(null);
		this.restartGame();
	}
	
	/**
	 * Gets the current player 1
	 * @return player1
	 */
	public Player getPlayer1() {
		return player1;
	}
	
	/**
	 * Gets the current player 2
	 * @return player2
	 */
	public Player getPlayer2() {
		return player2;
	}
	
	/**
	 * sets the current player 1
	 * @param p1 The new Player 1
	 */
	public void setPlayer1(Player p1) {
		var oldPlayer = player1;
		player1 = p1;
		onPlayer1Changed(oldPlayer);
	}
	
	/**
	 * Sets the current player 2
	 * @param p2 The new Player 2
	 */
	public void setPlayer2(Player p2) {
		var oldPlayer = player2;
		player2 = p2;
		onPlayer2Changed(oldPlayer);
	}
	
	/**
	 * Gets the active player based on Game State
	 * @return the currently acting player, may be null for some game states.
	 */
	public Player getActivePlayer() {
		if (gameState == GameState.PLAYER_1_TURN) {
			return player1;
		}
		else if (gameState == GameState.PLAYER_2_TURN) {
			return player2;
		}
		return null;
	}
	
	
	
	/**
	 * Getter for the player1WinCount
	 * @return player1WinCount
	 */
	public int getPlayer1WinCount() {
		return player1WinCount;
	}
	
	/**
	 * Getter for the player2WinCount
	 * @return player2WinCount
	 */
	public int getPlayer2WinCount() {
		return player2WinCount;
	}
	
	/**
	 * Sets the player 1 win count
	 * @param count new win count
	 */
	public void setPlayer1WinCount(int count) {
		player1WinCount = count;
		onGameWinCountChanged();
	}
	
	/**
	 * Sets the player 2 win count
	 * @param count new win count
	 */
	public void setPlayer2WinCount(int count) {
		player2WinCount = count;
		onGameWinCountChanged();
	}
	
	/**
	 * Sets a new listener for the game timer
	 * @param listener The new listener
	 */
	public void registerGameTimeListener(ControllableTimerChangeListener listener) {
		//gameTimer = new ControllableTimer(listener);
		gameTimer.setListener(listener);
		gameTimer.start();
	}
	
	/**
	 * Sets a new listener for the turn timer
	 * @param listener The new listener
	 */
	public void registerTurnTimeListener(ControllableTimerChangeListener listener) {
		turnTimer.setListener(listener);
		turnTimer.start();
		

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
	/** Property Name used to notify PropertyChange events when player 1 changes */
	public static final String GAME_PLAYER1_CHANGE_PROPERTY_NAME = "Player1Change";
	/** Property Name used to notify PropertyChange events when player 2 changes */
	public static final String GAME_PLAYER2_CHANGE_PROPERTY_NAME = "Player2Change";
	
	/**
	 * Registers a PropertyChangeListener to responds to changes to this model.
	 * Event will be fired upon changes to GameBoard, Players, GameState, etc.<br>
	 * 
	 * @param propertyName The name of the property for the listener to respond to.
	 * @param listener The {@link PropertyChangeListener} that responds to GameBoard being changed.
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
	 * Notify PropertyChangeListeners when the full GameBoard changes
	 */
	public void onGameBoardReset() {
		propertyChangedSupport.firePropertyChange(GAME_BOARD_FULL_PROPERTY_NAME, null, gameBoard.getBoardState());
	}
	
	/**
	 * Notify PropertyChangeListeners when the Game State changes
	 * @param newState The updated Game State
	 * @param oldState The previous Game State
	 */
	public void onGameStateChanged(GameState newState, GameState oldState) {
		propertyChangedSupport.firePropertyChange(GAME_STATE_PROPERTY_NAME, oldState, newState);
	}
	
	/**
	 * Notify PropertyChangeListeners when the player win counts change
	 */
	public void onGameWinCountChanged() {
		propertyChangedSupport.firePropertyChange(GAME_WIN_COUNT_PROPERTY_NAME, null,
			new GameWinCountChangedEvent(player1WinCount, player2WinCount));
	}
	
	/**
	 * Notify PropertyChangeListeners when player1 changes
	 * @param oldPlayer1 The previous player 1, before the change
	 */
	public void onPlayer1Changed(Player oldPlayer1) {
		propertyChangedSupport.firePropertyChange(GAME_PLAYER1_CHANGE_PROPERTY_NAME, oldPlayer1, player1);
	}
	
	/**
	 * Notify PropertyChangeListeners when player2 changes
	 * @param oldPlayer2 The previous player 2, before the change
	 */
	public void onPlayer2Changed(Player oldPlayer2) {
		propertyChangedSupport.firePropertyChange(GAME_PLAYER2_CHANGE_PROPERTY_NAME, oldPlayer2, player2);
	}
	
	/**
	 * Inner class used as oldValue and newValue in PropertyChange events for
	 * the {@value GameManager#GAME_BOARD_TILE_PROPERTY_NAME} property name.
	 */
	public class GameBoardPropertyChangedEvent{
		/** The row index of the tile update */
		public final int row;
		/** The column index of the tile update */
		public final int column;
		/** The state of the tile */
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
		/** The Player 1 win count */
		public final int player1WinCount;
		/** The Player 2 win count */
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
