/**
 * Connect4
 * Authors: Cole Brito, Paul Squires 
 * Section: 301
 * Professor: Daniel Cormier
 * Last Modified: March 24, 2024
 * Algonquin College CET-CS
 * JAP - Assignment 2-2
 */

package connectfour.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import connectfour.model.*;
import connectfour.view.GameBoardTile;
import connectfour.view.MainWindow;

/**
 * Controller that responds to actions on the GameBoard UI panels.
 * Relays actions to GameManager.
 */
public class GameController implements ActionListener {
	
	/** Reference to the GameManager */
	private GameManager gameManager;
	
	/**
	 * Instantiates the GameController and sets reference to the GameManager
	 */
	public GameController() {
        this.gameManager = GameManager.getInstance();
        System.out.println(gameManager);
    }

	/**
	 * Attempts to place a tile on the GameBoard
	 * @param column The column of the tile placement
	 * @param player The player that is placing the tile
	 */
    public void updateGameBoard(int column, int player) {
    	//TODO: move logic to this method to get active player
    	// and update game board.
    	//TODO: handle Network games as well
    }

    /**
     * Responds to actions sent from GameBoard UI elements
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof GameBoardTile) {
            GameBoardTile clickedTile = (GameBoardTile) e.getSource();
            short row = clickedTile.getRow();
            short column = clickedTile.getColumn();
            //TODO: Move logic to updateGameBoard method
            Player player = gameManager.activePlayer; //Placeholder
            
            System.out.println("Row clicked: " + row + ", Column clicked: " + column);
            
            gameManager.tryPlaceTile(row, column, player);
        }
    }
}