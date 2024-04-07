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

public class GameUpdateNetworkMessage extends NetworkMessage {

	/**
	 * Serial Version UID, used to compare class versions when deserializing
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Integer representing the GameState enum of the GameManager.
	 * May be null.
	 */
	public Integer gameState;
	
	/**
	 * Array of tile states.
	 * May be null.
	 */
	public int[][] gameBoard;
	
	/**
	 * The row of a single tile update
	 */
	public Integer row;
	
	/**
	 * The column of a single tile update
	 */
	public Integer column;
	
	/**
	 * The state of a single tile update
	 */
	public Integer state;
	
	/**
	 * Create a new GameUpdateNetworkMessage
	 * @param opcode {@link NetworkMessage#Opcode} The message opcode
	 * @param gameState the new gameState. Leave null if unchanged.
	 * @param gameBoard the updated array of tile states. Leave null if unchanged.
	 */
	public GameUpdateNetworkMessage(Opcode opcode, Integer gameState, int[][] gameBoard) {
		super(opcode);
		this.gameState = gameState;
		this.gameBoard = gameBoard;
	}
	
	/**
	 * Create a new GameUpdateNetworkMessage
	 * @param opcode {@link NetworkMessage#Opcode} The message opcode
	 * @param gameState the new gameState. Leave null if unchanged.
	 * @param gameBoard the updated array of tile states. Leave null if unchanged.
	 */
	public GameUpdateNetworkMessage(Opcode opcode, Integer row, Integer column, Integer state) {
		super(opcode);
		this.row = row;
		this.column = column;
		this.state = state;
	}
}
