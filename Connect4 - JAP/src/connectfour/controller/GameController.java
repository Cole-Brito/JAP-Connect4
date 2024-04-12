/**
 * Connect4
 * Authors: Cole Brito, Paul Squires 
 * Section: 301
 * Professor: Daniel Cormier
 * Last Modified: April 12, 2024
 * Algonquin College CET-CS
 * JAP - Assignment 3-2
 */

package connectfour.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import connectfour.model.*;
import connectfour.model.network.NetworkManager;
import connectfour.model.network.NetworkManager.SessionType;
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
	 */
    public void updateGameBoard(int column) {
    	//TODO: move logic to this method to get active player
    	// and update game board.
    	//TODO: handle Network games as well
    	var networkManager = NetworkManager.getInstance();
    	switch(networkManager.getSessionType()) {
		case CLIENT:
		{
			networkManager.sendGameBoardUpdateMessage(null, column, null);
		}
			break;
		case HOST:
		{
			// Use localPlayer1 as the host's player
			gameManager.tryPlaceTile(column, PlayerManager.getInstance().getLocalPlayer1());
		}
			break;
		case OFFLINE:
		{
			// Assume local players in offline mode
            gameManager.tryPlaceTile(column, gameManager.getActivePlayer());
		}
			break;
		default:
			System.err.println("Received incorrect Network SessionType in GameController");
			break;
    	
    	}
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
            
            System.out.println("Row clicked: " + row + ", Column clicked: " + column);
            
            updateGameBoard(column);
        }
    }
}