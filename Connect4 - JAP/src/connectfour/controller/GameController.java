package connectfour.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import connectfour.model.*;
import connectfour.view.GameBoardTile;
import connectfour.view.MainWindow;

//make singleton
public class GameController implements ActionListener {
	
	private GameManager gameManager;
	public GameController() {
        this.gameManager = GameManager.getInstance();
        System.out.println(gameManager);
    }

    public void updateGameBoard(int column, int player) {
    	
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
            //this code is adjusted from Dainel Corimer
        } else if (e.getSource() instanceof MainWindow){
        	String item = e.getActionCommand();
        	switch(item) {
        	case "load":
        		break;
        	case "save":
        		break;
        	case "restart":
        		
        		break;
        	case "playerlist":
        		break;
        	case "mode":
        		break;
        	case "host":
        		break;
        	case "connect":
        		break;
        	case "disconnect":
        		break;
        	case "english":
        		break;
        	case "french":
        		break;
        	case "controls":
        		break;
        	case "theme":
        		break;
        	case "about":
        		break;
        	case "access":
        		break;
        	}
        	
        }
    }
}