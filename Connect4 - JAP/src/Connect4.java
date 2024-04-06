/**
 * Connect4
 * Authors: Cole Brito, Paul Squires 
 * Section: 301
 * Professor: Daniel Cormier
 * Last Modified: March 24, 2024
 * Algonquin College CET-CS
 * JAP - Assignment 2-2
 */

import java.util.UUID;

import connectfour.controller.ChatController;
import connectfour.controller.GameController;
import connectfour.controller.MenuController;
import connectfour.model.ChatManager;
import connectfour.model.GameManager;
import connectfour.model.PlayerManager;
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
		menuController = new MenuController(mainWindow);
		LocaleManager.getInstance().registerLocaleChangeListener(mainWindow);
		LocaleManager.getInstance().registerLocaleChangeListener(mainWindow.gameInfoPanel);
		mainWindow.gameBoardPanel.registerTileActionListener(gameController);
		mainWindow.registerMenuListeners(menuController);
		var gameManager = GameManager.getInstance();		
		gameManager.registerPropertyChangeListener(GameManager.GAME_BOARD_TILE_PROPERTY_NAME, mainWindow.gameBoardPanel);
		gameManager.registerPropertyChangeListener(GameManager.GAME_BOARD_FULL_PROPERTY_NAME, mainWindow.gameBoardPanel);
		gameManager.registerPropertyChangeListener(GameManager.GAME_STATE_PROPERTY_NAME, mainWindow);
		gameManager.registerPropertyChangeListener(GameManager.GAME_WIN_COUNT_PROPERTY_NAME, mainWindow.gameInfoPanel);
		gameManager.registerPropertyChangeListener(GameManager.GAME_PLAYER1_CHANGE_PROPERTY_NAME, mainWindow.gameInfoPanel);
		gameManager.registerPropertyChangeListener(GameManager.GAME_PLAYER2_CHANGE_PROPERTY_NAME, mainWindow.gameInfoPanel);
		
		var playerManager = PlayerManager.getInstance();
		playerManager.registerPropertyChangeListener(PlayerManager.PLAYER_UPDATE_PROPERTY_NAME, mainWindow.gameInfoPanel);
		//mainWindow.gameInfoPanel.setPlayer1(playerManager.getLocalPlayer1());
		//mainWindow.gameInfoPanel.setPlayer2(playerManager.getLocalPlayer2());
		mainWindow.gameInfoPanel.setPlayer1(gameManager.getPlayer1());
		mainWindow.gameInfoPanel.setPlayer2(gameManager.getPlayer2());
		
		chatController = new ChatController(mainWindow.chatTextInputField);
		
		gameManager.registerGameTimeListener(mainWindow.gameInfoPanel.getGameTimer());
		gameManager.registerTurnTimeListener(mainWindow.gameInfoPanel.getTurnTimer());

		ChatManager.getInstance().addSystemMessage("Welcome to Connect4!");
		ChatManager.getInstance().addMessage("Hello", gameManager.getPlayer1());
		ChatManager.getInstance().addMessage("Hi", gameManager.getPlayer2());
	}

}
