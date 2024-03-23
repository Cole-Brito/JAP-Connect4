import connectfour.controller.ChatController;
import connectfour.controller.GameController;
import connectfour.model.ChatManager;
import connectfour.model.GameManager;
import connectfour.model.locale.LocaleManager;
import connectfour.view.MainWindow;

/**
 * 
 * @author Cole Brito
 *
 */

public class Connect4 {

	private static MainWindow mainWindow;
	
	private static GameController gameController;
	private static ChatController chatController;
	
	/** 
	 * Program entry point. WIP
	 * @param args Command line arguments, currently unused
	 * */
	public static void main(String[] args) {

		//System.out.println(Connect4.class.getResource("/locales/locale_EN.txt").getFile());
		//TODO: get locale filenames from config file
		LocaleManager.getInstance().loadLanguageSet("/locales/locale_EN.txt");
		LocaleManager.getInstance().loadLanguageSet("/locales/locale_FR.txt");
		
		mainWindow = new MainWindow();
		mainWindow.setVisible(true);
		
		gameController = new GameController();
		mainWindow.gameBoardPanel.registerTileActionListener(gameController);
		GameManager.getInstance().registerGameBoardPropertyChangeListener(mainWindow.gameBoardPanel);
		
		chatController = new ChatController(mainWindow.chatTextInputField);

	}

}
