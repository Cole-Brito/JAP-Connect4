/**
 * Connect4
 * Authors: Cole Brito, Paul Squires 
 * Section: 301
 * Professor: Daniel Cormier
 * Last Modified: March 24, 2024
 * Algonquin College CET-CS
 * JAP - Assignment 2-2
 */

package connectfour.view;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.border.Border;

import connectfour.model.GameManager;
import connectfour.model.GameManager.GameBoardPropertyChangedEvent;
/**
 * Class to generate and control the behavior of the game board
 * @author Cole Brito, Paul Squires
 *
 */
public class GameBoardPanel implements PropertyChangeListener {
	
	/** Number of rows to use in GameBoardTile array */
	private final static int NUM_ROWS = 6;
	/** Number of columns to use in GameBoardTile array */
	private final static int NUM_COLUMNS = 7;
	
	/** Array of tile JButtons*/
	GameBoardTile[][] tiles = new GameBoardTile[NUM_ROWS][NUM_COLUMNS];

	/**
	 * generates a grid of 52 x 52 pixel buttons holding an image of an empty tile space.
	 * @param content - The panels this is being added to
	 * @param c - the constrains from the gridbagLayout
	 */
	protected void generateTileGrid(JPanel content, GridBagConstraints c) {
		//Grid of buttons 	
		Border blackLine = BorderFactory.createLineBorder(Color.BLACK);
			for (int i = 0; i < tiles.length; i++) {
				for (int j = 0; j < tiles[i].length; j++) {
					tiles[i][j] = new GameBoardTile(i, j);
		            tiles[i][j].updateState(GameBoardTile.TileState.DEFAULT);
					tiles[i][j].setBorder(blackLine);
					c.gridx = j;
					c.gridy = i;
					content.add(tiles[i][j], c);
				}
			}
	}
	
	/**
     * Registers an ActionListener for all GameBoardTiles.
     * @param listener The ActionListener to register.
     */
    public void registerTileActionListener(ActionListener listener) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j].addActionListener(listener);
            }
        }
    }
//	/** Action Listener for testing tile placements*/
//	ActionListener actionListener = new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            JButton source = (JButton) e.getSource();
//            // Find the source button in the tile 2D array
//            for (int i = 0; i < tiles.length; i++) {
//                for (int j = 0; j < tiles[i].length; j++) {
//                    if (tiles[i][j] == source) {
//                        System.out.println("Button at row " + i + ", column " + j + " was clicked.");
//                        tiles[i][j].updateState(TileState.PLAYER_1); //Temp, testing the listener
//                        return;
//                    }
//                }
//            }
//        }
//    };
    
    /**
     * Updates the state of a single tile
     * @param row The row index of the tile
     * @param column The column index of the tile
     * @param state The new state for the tile
     */
    public void updateBoardState(int row, int column, int state) {
    	assert(row >= 0 && row < NUM_ROWS && column >= 0 && column < NUM_COLUMNS);
    	this.tiles[row][column].updateState(state);
    }
    
    /**
     * Update the entire game board with new tile states
     * @param tiles The 2d array of tile states
     */
    public void updateFullBoard(int[][] tiles) {
    	assert(tiles.length == NUM_ROWS && tiles[0].length == NUM_COLUMNS);
    	for(int row = 0; row < tiles.length; ++row) {
    		for(int column = 0; column < tiles.length; ++column) {
    			updateBoardState(row, column, tiles[row][column]);
    		}
    	}
    }

    /**
     * PropertyChange event that responds to changes in the GameBoard model
     */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName() == GameManager.GAME_BOARD_TILE_PROPERTY_NAME){
			var gameBoardEvent = (GameManager.GameBoardPropertyChangedEvent)evt.getNewValue();
			if (gameBoardEvent != null) {
				updateBoardState(gameBoardEvent.row, gameBoardEvent.column, gameBoardEvent.state);
			}
			else {
				System.err.println("[GameBoardPanel$propertyChange] Received PropertyChanged newValue was null or "
						+ "not of type GameManager.GameBoardPropertyChangedEvent.\n"
						+ "PropertyName: " + evt.getPropertyName());
			}
		}
		else if (evt.getPropertyName() == GameManager.GAME_BOARD_FULL_PROPERTY_NAME){
			var gameBoard = (int[][])evt.getNewValue();
			if (gameBoard != null || gameBoard.length < NUM_ROWS || gameBoard[0].length < NUM_COLUMNS) {
				updateFullBoard(gameBoard);
			}
			else {
				System.err.println("[GameBoardPanel$propertyChange] Received PropertyChanged newValue was null or "
						+ "not of type int[][].\n"
						+ "PropertyName: " + evt.getPropertyName());
			}
		}
	}
	
}
