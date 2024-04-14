/**
 * Connect4
 * Authors: Cole Brito, Paul Squires 
 * Section: 301
 * Professor: Daniel Cormier
 * Last Modified: April 11, 2024
 * Algonquin College CET-CS
 * JAP - Assignment 3-2
 */

package connectfour.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import connectfour.model.GameManager;
import connectfour.model.locale.LocaleManager;
import connectfour.model.network.NetworkManager;
import connectfour.model.network.NetworkManager.SessionType;
import connectfour.view.MainWindow;
import connectfour.view.NetworkDialogs;
import connectfour.view.PlayerListDialog;

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
	/**Reference to NetworkDialogs*/
	private NetworkDialogs networkDialog;
	/** Reference to PlayerListDialog */
	private PlayerListDialog playerListDialog;
	
	/**
	 * Instantiate the MenuController and set the GameManager and MainWindow reference
	 * @param mainWindow reference to the MainWindow
	 */
	public MenuController(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		this.gameManager = GameManager.getInstance();
		this.networkDialog = new NetworkDialogs();
		this.playerListDialog = new PlayerListDialog();
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
    		//TODO: may be removed
    		break;
    	case "save":
    		//TODO: may be removed
    		break;
    	case "restart":	
    		System.out.println("Restarting...");
    		if (NetworkManager.getInstance().getSessionType() != SessionType.CLIENT) {
    			gameManager.restartGame();    			
    		}
    		break;
    	case "playerlist":
    		playerListDialog.showPlayerList();
    		break;
    	case "mode":
    		break;
    	case "host":
    		networkDialog.diplayHostSetup();
    		break;
    	case "connect":
    		networkDialog.displayConnectionSetup();
    		if (NetworkManager.getInstance().getSessionType() != SessionType.CLIENT) {
    			var dialog = new JDialog(mainWindow, "Could not start server.");
    		}
    		break;
    	case "disconnect":
    		NetworkManager.getInstance().closeServerSocket();
    		NetworkManager.getInstance().closeClientSocket(true);
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
    		//TODO: may be removed
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
