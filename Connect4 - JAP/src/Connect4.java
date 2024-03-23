import connectfour.controller.ChatController;
import connectfour.controller.GameController;
import connectfour.model.ChatManager;
import connectfour.model.GameManager;
import connectfour.view.MainWindow;
import connectfour.view.SplashScreen;

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
		
		mainWindow = new MainWindow();
		mainWindow.setVisible(true);
		
		gameController = new GameController();
		mainWindow.gameBoardPanel.registerTileActionListener(gameController);
		GameManager.getInstance().registerGameBoardPropertyChangeListener(mainWindow.gameBoardPanel);
		
		chatController = new ChatController(mainWindow.chatTextInputField);

	}

}
