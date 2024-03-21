import connectfour.controller.ChatController;
import connectfour.model.ChatManager;
import connectfour.view.MainWindow;

/**
 * 
 * @author Cole Brito
 *
 */

public class Connect4 {

	/** 
	 * Program entry point. WIP
	 * @param args Command line arguments, currently unused
	 * */
	public static void main(String[] args) {
		
		MainWindow view = new MainWindow();
		view.setVisible(true);
		
		//GameController gameController = new GameController();
		ChatController chatController = new ChatController(ChatManager.getInstance(), view.chatTextInputField);

	}

}
