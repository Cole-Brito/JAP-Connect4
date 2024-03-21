package connectfour.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import connectfour.model.*;
import connectfour.view.GameBoardTile;

//make singleton
public class GameContoller implements ActionListener {
	
	private GameManager gameManager;

	public void GameController(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void updateGameBoard(short column, short player) {
    	
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof GameBoardTile) {
            GameBoardTile clickedTile = (GameBoardTile) e.getSource();
            short row = clickedTile.getRow();
            short column = clickedTile.getColumn();
            Player player = gameManager.activePlayer; //Placeholder
            
            System.out.println("Row clicked: " + row + ", Column clicked: " + column);
            
            gameManager.tryPlaceTile(row, column, player);
        }
    }
}