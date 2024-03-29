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

import connectfour.model.locale.LocaleManager;
import connectfour.view.MainWindow;
import connectfour.model.GameManager;

/**
 * Controller that responds to Menu UI elements and relays
 * actions to appropriate models.
 */
public class MenuController implements ActionListener {
	
	//private static final String MENU_LANGUAGE_EN = "english";
	//private static final String MENU_LANGUAGE_FR = "french";

	/** Reference to the GameManager */
	private GameManager gameManager;
	/**Reference to MainWindow*/
	private MainWindow mainWindow;
	
	/**
	 * Instantiate the MenuController and set the GameManager and MainWindow reference
	 * @param mainWindow reference to the MainWindow
	 */
	public MenuController(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		this.gameManager = GameManager.getInstance();
	}

	/**
	 * Respond to ActionEvents from the menu.
	 * Perform a different task based on action command.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String item = e.getActionCommand();
    	switch(item) {
    	case "load":
    		break;
    	case "save":
    		break;
    	case "restart":	
    		System.out.println("Restarting...");
    		gameManager.restartGame();
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
    		LocaleManager.getInstance().setActiveLanguageSet("EN");
    		break;
    	case "french":
    		LocaleManager.getInstance().setActiveLanguageSet("FR");
    		break;
    	case "controls":
    		mainWindow.displayControls();
    		break;
    	case "theme":
    		break;
    	case "about":
    		mainWindow.displayAbout();
    		break;
    	case "access":
    		break;
    	default:
    		System.err.println("Received invalid action command in MenuController: " + item);
    		break;
    	}
	}
	
	
}
