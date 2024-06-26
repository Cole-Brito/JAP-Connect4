/**
 * Connect4
 * Authors: Cole Brito, Paul Squires 
 * Section: 301
 * Professor: Daniel Cormier
 * Last Modified: April 7, 2024
 * Algonquin College CET-CS
 * JAP - Assignment 3-2
 */

package connectfour.model.network;

import java.io.Serializable;

/**
 * A class the represents a NetworkMessage containing all necessary information
 * for a new client connections. Contains Game States, Game Board, and Player List.
 */
public class HelloNetworkMessage extends GameUpdateNetworkMessage {

	/**
	 * Serial Version UID, used to compare class versions when deserializing
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * List of players
	 */
	public PlayerPayload[] players;

	/**
	 * Create a new HelloNetworkMessage
	 * @param opcode {@link Opcode} The message opcode
	 * @param gameState the initial gameState
	 * @param gameBoard the array of tile states
	 * @param players the list of players
	 */
	public HelloNetworkMessage(Opcode opcode, Integer gameState, int[][] gameBoard, PlayerPayload[] players) {
		super(opcode, gameState, gameBoard);
		this.players = players;
	}
	
	/**
	 * Create a new HelloNetworkMessage
	 * @param opcode {@link Opcode} The message opcode
	 * @param gameState the initial gameState
	 * @param gameBoard the array of tile states
	 */
	public HelloNetworkMessage(Opcode opcode, Integer gameState, int[][] gameBoard) {
		super(opcode, gameState, gameBoard);
	}
	
	/**
	 * Create a new HelloNetworkMessage
	 * @param opcode {@link Opcode} The message opcode
	 * @param gameState the initial gameState
	 * @param gameBoard the array of tile states
	 * @param p1wins the player 1 win count
	 * @param p2wins the player 2 win count
	 */
	public HelloNetworkMessage(Opcode opcode, Integer gameState, int[][] gameBoard, Integer p1wins, Integer p2wins) {
		super(opcode, gameState, gameBoard, p1wins, p2wins);
	}

	/**
	 * An inner class to represent Player objects in the HelloMessage, so player list can be sent to clients.
	 */
	public class PlayerPayload implements Serializable{
		/**
		 * The player's username.
		 */
		public String username;
		
		/**
		 * The player's UUID. Should never be null.
		 */
		public String uID;
		
		/**
		 * The player's state.
		 * <br>
		 * <br>1 = player 1
		 * <br>2 = player 2
		 * <br>0 = default (spectator)
		 */
		public Integer playerState;
		
		/**
		 * Create a new PlayerPayload
		 * @param name the player's username
		 * @param id the player's UUID (as a String)
		 * @param state the player's state (ie. p1, p2, or spectator)
		 */
		public PlayerPayload(String name, String id, Integer state) {
			this.username = name;
			this.uID = id;
			this.playerState = state;
		}
	}
}
