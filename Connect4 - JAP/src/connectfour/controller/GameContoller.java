package connectfour.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import connectfour.model.*;

public class GameContoller implements ActionListener {
	
	private GameBoard gameBoard;

	public void GameController(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void updateGameBoard(short column, short player) {
    	for (short i = 0; i < 6; i++) {
    		gameBoard.setTileState(i, column, player);	
    	}
    	gameBoard.checkWinStates(player, column, player);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		String input = e.getActionCommand();
        String[] vars = input.split(",");
        System.out.println("Cols: "+vars[0]);
        System.out.println("Rows: "+vars[1]);
        
		
	}
}