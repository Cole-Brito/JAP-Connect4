package connectfour.controller;

import connectfour.model.*;

public class GameContoller {
	
	private GameBoard gameBoard;

	public void GameController(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void actionPerformed() {
        
    }

    public void updateGameBoard(short column, short player) {
    	for (short i = 0; i < 6; i++) {
    		gameBoard.setTileState(i, column, player);	
    	}
    	gameBoard.checkWinStates(player, column, player);
    }
}