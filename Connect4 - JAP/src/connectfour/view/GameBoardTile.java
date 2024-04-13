/**
 * Connect4
 * Authors: Cole Brito, Paul Squires 
 * Section: 301
 * Professor: Daniel Cormier
 * Last Modified: April 7, 2024
 * Algonquin College CET-CS
 * JAP - Assignment 3-2
 */

package connectfour.view;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * JButton used to display a single tile on the game board.
 * Sends ActionEvent when clicked.
 */
public class GameBoardTile extends JButton{
	
	/**
	 * Default serial version ID to use in deserializing
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Valid tile states for this tile.
	 */
	public enum TileState {
		/** Default state, no tile */
		DEFAULT,
		/** Player1 tile placed */
		PLAYER_1,
		/** Player2 tile placed */
		PLAYER_2;
	}
	
	//declaring asset variables 
	/** Default empty tile slot*/
	ImageIcon emptyTile = new ImageIcon(getClass().getResource("/images/emptyTile.png"));
	/** Default player 1 tile slot*/
	ImageIcon redTile = new ImageIcon(getClass().getResource("/images/redTile.png"));
	/** Default player 2 tile slot*/
	ImageIcon blackTile = new ImageIcon(getClass().getResource("/images/blackTile.png"));
	
	/** The current state of this tile (ie. empty, player1, player2) */
	private TileState state;
	
	/** The column index that this tile represents */
	private short column;
	/** The row index that this tile represents */
	private short row;
	/** The icon to use for player 1 tiles */
	protected ImageIcon player1Icon;
	/** The icon to use for player 2 tiles */
	protected ImageIcon player2Icon;
	/** The default icon to use for no tile */
	protected ImageIcon emptyIcon;
	
	/**
	 * Instantiate this GameBoardTile with new position
	 * @param row The row index this tile represents
	 * @param column The column index this tile represents
	 */
	public GameBoardTile(int row, int column) {
		this.row = (short) row;
	    this.column = (short) column;
	    emptyIcon = new ImageIcon(getClass().getResource("/images/emptyTile.png"));
	    player1Icon = new ImageIcon(getClass().getResource("/images/redTile.png"));
	    player2Icon = new ImageIcon(getClass().getResource("/images/blackTile.png"));
	    setPreferredSize(new Dimension(52, 52));
	}

	/**
	 * Updates this tile with a new state.
	 * Also updates icon accordingly.
	 * @param state The new TileState
	 */
	public void updateState(TileState state) {
		this.state = state;
		switch (state) {
		case PLAYER_1:
			setIcon(player1Icon);
			break;
		case PLAYER_2:
			setIcon(player2Icon);
			break;
		case DEFAULT:
			setIcon(emptyIcon);
			break;
		}
	}
	
	/**
	 * Updates this tile with a new state, using the numerical value
	 * @param state The numerical value of the new state
	 */
	public void updateState(int state) {
		if (state >= 0 && state < TileState.values().length) {
			updateState(TileState.values()[state]);
		}
	}
	
	/**
	 * Gets the row index of this tile
	 * @return row index
	 */
	public short getRow() {
        return row;
    }

	/**
	 * Gets the column index of this tile
	 * @return column index
	 */
    public short getColumn() {
        return column;
    }
}
