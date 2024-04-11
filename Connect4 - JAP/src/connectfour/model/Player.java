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

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

/**
 * This class is used for creating player objects 
 */
public class Player {
	/** The display name for this player */
	private String username;
	/** The type of player (local or network) */
	private PlayerType playerType;
	
	/** The ID of the player, used to uniquely identify outside of username */
	private final UUID uID;
	
	/**
	 * Instantiate a new player
	 * @param name The username of the player
	 * @param type The PlayerType of the player
	 */
	Player(String name, PlayerType type){
		this.username = name;
		this.playerType = type;
		this.uID = UUID.randomUUID();
	}
	
	/**
	 * Instantiate a new player with a client socket connection
	 * @param name The username of the player
	 * @param type The PlayerType of the player
	 * @param socket The client's socket connection to the server
	 */
	Player(String name, String id, PlayerType type){
		this.username = name;
		this.playerType = type;
		this.uID = UUID.fromString(id);
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
	
	/**
	 * Get the player's UUID
	 * @return player UUID
	 */
	public UUID getPlayerID() {
		return uID;
	}
	
	/**
	 * Checks if a given string matches the player's UUID
	 * @param id The id to compare
	 * @return true if the id matches
	 */
	public boolean idMatchesPlayerID(String id) {
		return uID.toString().equals(id);
	}
	
	/**
	 * Player objects are considered equal if their UUIDs are the same
	 */
	@Override
	public boolean equals(Object obj) {
		Player other = (Player)obj;
		if (other != null) {
			return this.uID.equals(other.uID);
		}
		return super.equals(obj);
	}
	
	@Override
	public String toString() {
		return String.format("[%s : %s : %s]", username, uID.toString(), playerType.toString());
	}
}
