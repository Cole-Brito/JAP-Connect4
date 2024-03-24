package connectfour.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import connectfour.model.locale.LocaleManager;
import connectfour.model.GameManager;

public class MenuController implements ActionListener {
	
	//private static final String MENU_LANGUAGE_EN = "english";
	//private static final String MENU_LANGUAGE_FR = "french";

	private GameManager gameManager;
	
	public MenuController() {
		this.gameManager = GameManager.getInstance();
        System.out.println(gameManager);	
	}

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
    		break;
    	case "theme":
    		break;
    	case "about":
    		break;
    	case "access":
    		break;
    	default:
    		System.err.println("Received invalid action command in MenuController: " + item);
    		break;
    	}
	}
	
	
}
