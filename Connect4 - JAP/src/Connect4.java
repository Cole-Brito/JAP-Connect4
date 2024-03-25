/**
 * Connect4
 * Authors: Cole Brito, Paul Squires 
 * Section: 301
 * Professor: Daniel Cormier
 * Last Modified: March 24, 2024
 * Algonquin College CET-CS
 * JAP - Assignment 2-2
 */

import connectfour.controller.ChatController;
import connectfour.controller.GameController;
import connectfour.controller.MenuController;
import connectfour.model.ChatManager;
import connectfour.model.GameManager;
import connectfour.model.locale.LocaleManager;
import connectfour.view.MainWindow;
import connectfour.view.SplashScreen;

/**
 * Main application class. Contains main function.
 * Stores references and handles initialization of necessary models, views and controllers
 */
public class Connect4 {

	/** Reference to the MainWindow */
	private static MainWindow mainWindow;
	
	/** Reference to the GameController, keeps controller loaded. */
	private static GameController gameController;
	/** Reference to the ChatController, keeps controller loaded. */
	private static ChatController chatController;
	/** Reference to the MenuController, keeps controller loaded. */
	private static MenuController menuController;
	
	/** 
	 * Program entry point. WIP
	 * @param args Command line arguments, currently unused
	 * */
	public static void main(String[] args) {

		//Running the splash screen for 5 seconds - code adjusted from Daniel Corimer
		SplashScreen splashScreen = new SplashScreen();
		int delay = 5000;
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e){
			e.printStackTrace();
		}
		splashScreen.setVisible(true);
		
		splashScreen.dispose();

		//System.out.println(Connect4.class.getResource("/locales/locale_EN.txt").getFile());
		//TODO: get locale filenames from config file
		LocaleManager.getInstance().loadLanguageSet("/locales/locale_EN.txt");
		LocaleManager.getInstance().loadLanguageSet("/locales/locale_FR.txt");

		
		mainWindow = new MainWindow();
		mainWindow.setVisible(true);
		
		gameController = new GameController();
		menuController = new MenuController();
		LocaleManager.getInstance().registerLocaleChangeListener(mainWindow);
		mainWindow.gameBoardPanel.registerTileActionListener(gameController);
		mainWindow.registerMenuListeners(menuController);
		var gameManager = GameManager.getInstance();		
		gameManager.registerPropertyChangeListener(GameManager.GAME_BOARD_TILE_PROPERTY_NAME, mainWindow.gameBoardPanel);
		gameManager.registerPropertyChangeListener(GameManager.GAME_STATE_PROPERTY_NAME, mainWindow);
		gameManager.registerPropertyChangeListener(GameManager.GAME_WIN_COUNT_PROPERTY_NAME, mainWindow.gameInfoPanel);		
		
		chatController = new ChatController(mainWindow.chatTextInputField);

	}

}
