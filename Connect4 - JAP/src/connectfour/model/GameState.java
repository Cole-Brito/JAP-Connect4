/**
 * Connect4
 * Authors: Cole Brito, Paul Squires 
 * Section: 301
 * Professor: Daniel Cormier
 * Last Modified: March 24, 2024
 * Algonquin College CET-CS
 * JAP - Assignment 2-2
 */

package connectfour.model;

/** Enum of valid Game States */
public enum GameState {
	/** Player 1 turn, uses player ID 1 */
	PLAYER_1_TURN(1),
	/** Player 2 turn, uses player ID 2 */
	PLAYER_2_TURN(2),
	/** Game end state with Player 1 win */
	PLAYER_1_WIN,
	/** Game end state with Player 2 win */
	PLAYER_2_WIN,
	/** Game end state with no win */
	DRAW,
	/** Default (inactive) game state */
	DEFAULT;
	
	/** The active player for this state. -1 for all non active player states */
	private final int playerID;
	
	/** 
	 * Gets the player ID that should be active for this state 
	 * @return The player ID
	 */
	public int getPlayerID() {
		return playerID;
	}
	
	/** Default GameState constructor, uses -1 for player ID */
	private GameState() {
		playerID = -1;
	}

	/**
	 * GameState constructor, where player ID is specified
	 * @param playerID The player ID used for this enum state, should only be 1 or 2
	 */
	private GameState(int playerID) {
		this.playerID = playerID;
	}
}
