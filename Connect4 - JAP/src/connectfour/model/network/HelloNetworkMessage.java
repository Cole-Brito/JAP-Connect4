/**
 * Connect4
 * Authors: Cole Brito, Paul Squires 
 * Section: 301
 * Professor: Daniel Cormier
 * Last Modified: April 5, 2024
 * Algonquin College CET-CS
 * JAP - Assignment 2-2
 */

package connectfour.model.network;

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
	 * @param opcode {@link NetworkMessage#Opcode} The message opcode
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
	 * @param opcode {@link NetworkMessage#Opcode} The message opcode
	 * @param gameState the initial gameState
	 * @param gameBoard the array of tile states
	 */
	public HelloNetworkMessage(Opcode opcode, Integer gameState, int[][] gameBoard) {
		super(opcode, gameState, gameBoard);
	}

	public class PlayerPayload{
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
		 * <list>
		 * <li>1 = player 1</li>
		 * <li>2 = player 2</li>
		 * <li>0 = default (spectator)</li>
		 * </list>
		 */
		public Integer playerState;
		
		public PlayerPayload(String name, String id, Integer state) {
			this.username = name;
			this.uID = id;
			this.playerState = state;
		}
	}
}
