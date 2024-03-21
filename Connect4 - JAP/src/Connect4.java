import connectfour.controller.ChatController;
import connectfour.controller.GameController;
import connectfour.model.ChatManager;
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
		
		mainWindow = new MainWindow();
		mainWindow.setVisible(true);
		
		gameController = new GameController();
		mainWindow.gameBoardPanel.registerTileActionListener(gameController);
		
		chatController = new ChatController(mainWindow.chatTextInputField);

	}

}
