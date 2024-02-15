/**
 * The purpose of this class is to hold the JFrame that will hold all subsequent
 * containers and menu options
 * Authors: Cole Brito, Paul Squires 
 * Professor: 
 * Last Modified:
 * Algonquin College CET-CS
 */
package connectfour.view;
import javax.swing.*;
import java.awt.*;
/**
 * 
 * @author Cole Brito
 *
 */
public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8215973777232473220L;
	/**
	 * 
	 */
	public MainWindow() {
		super("Connect 4");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//690 x 430 was what was on our draft but seems small now
		setMinimumSize(new Dimension(690,430));
		pack();
		setVisible(true);
		//Creating the menu bar
		generateMenuBar();
		//Used for content bg
		Color baseBlue = new Color(159, 181, 218);
		
		//Used for panels that display game info (chat box, game info box, turn signifier)
		//Color infoBlue = new Color(190, 205, 230); 
		
		JPanel content = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		content.setBackground(baseBlue);
		add(content);
		Gameboard gameboard = new Gameboard();
		gameboard.generateTileGrid(content, c);
		GameInfo gi = new GameInfo();
		content.add(gi);
		
		
		
	} //end of main window

	
	/**
	 * Method to create the menu bar and its items 
	 */
	private void generateMenuBar() {
		//Adding the menu bar to the window
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		//Creating MenuBar items
		
		//File menu and options
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		JMenuItem saveItem = new JMenuItem("Save", null);
		fileMenu.add(saveItem);
		JMenuItem loadItem = new JMenuItem("Load", null);
		fileMenu.add(loadItem);
		//Game menu and options
		JMenu gameMenu = new JMenu("Game");
		menuBar.add(gameMenu);
		JMenuItem restartItem = new JMenuItem("Restart", null);
		gameMenu.add(restartItem);
		JMenuItem playerItem = new JMenuItem("Player List", null);
		gameMenu.add(playerItem);
		JMenuItem modeItem = new JMenuItem("Gamemode", null);
		gameMenu.add(modeItem);
		//Network menu and options
		JMenu networkMenu = new JMenu("Network");
		menuBar.add(networkMenu);
		JMenuItem hostItem = new JMenuItem("Host", null);
		networkMenu.add(hostItem);
		JMenuItem connectItem = new JMenuItem("Connect", null);
		networkMenu.add(connectItem);
		JMenuItem disconnectItem = new JMenuItem("Disconnect", null);
		networkMenu.add(disconnectItem);
		//Language menu and options
		JMenu langMenu = new JMenu("Language");
		menuBar.add(langMenu);
		JMenuItem englishItem = new JMenuItem("English", null);
		langMenu.add(englishItem);
		JMenuItem frenchItem = new JMenuItem("French", null);
		langMenu.add(frenchItem);
		//Help menu and options
		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		JMenuItem controlsItem = new JMenuItem("How to Play", null);
		helpMenu.add(controlsItem);
		JMenuItem aboutItem = new JMenuItem("About", null);
		helpMenu.add(aboutItem);
		//View menu and options
		JMenu viewMenu = new JMenu("View");
		menuBar.add(viewMenu);
		JMenuItem themeItem = new JMenuItem("Theme", null);
		viewMenu.add(themeItem);
		JMenuItem accessItem = new JMenuItem("Accessablity", null);
		viewMenu.add(accessItem);
		
		//Setting Mnemonics
		fileMenu.setMnemonic('F');
		gameMenu.setMnemonic('G');
		langMenu.setMnemonic('L');
		viewMenu.setMnemonic('V');
		helpMenu.setMnemonic('H');
		networkMenu.setMnemonic('N');
		
//		loadItem.addActionListener();
		
	}
	
}
