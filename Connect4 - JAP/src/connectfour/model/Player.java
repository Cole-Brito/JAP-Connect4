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

/**
 * This class is used for creating player objects 
 */
public class Player {
	/** The display name for this player */
	private String username;
	/** The type of player (local or network) */
	private PlayerType playerType;
	
	/**
	 * Instantiate a new player
	 * @param name The username of the player
	 * @param type The PlayerType of the player
	 */
	Player(String name, PlayerType type){
		this.username = name;
		this.playerType = type;
	}
	
	/**
	 * Getter for the player's username
	 * @return The username
	 */
	public String getName() {
		return username;
	}
	
	/**
	 * Setter for the player's username
	 * @param name The new username
	 */
	public void setName(String name) {
		username = name;
	}
	
	/**
	 * Getter for the player's PlayerType
	 * @return The player's PlayerType
	 */
	public PlayerType getPlayerType() {
		return playerType;
	}
	
	/**
	 * Setter for the player's PlayerType
	 * @param type The new PlayerType
	 */
	public void setPlayerType(PlayerType type) {
		this.playerType = type;
	}
}
