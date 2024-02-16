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
 * @author Cole Brito, Paul Squires
 *
 */
public class MainWindow extends JFrame {

	/**
	 * Serial Version UID, used to compare class versions when deserializing
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public MainWindow() {
		super("Connect 4");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//690 x 430 was what was on our draft but seems small now
		setMinimumSize(new Dimension(690,430));
		//Creating the menu bar
		generateMenuBar();
		//Used for content bg
		Color baseBlue = new Color(159, 181, 218);
		
		//Used for panels that display game info (chat box, game info box, turn signifier)
		//Color infoBlue = new Color(190, 205, 230); 
		
		JPanel content = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.add(content);
		
		var mainBoardPanel = new JPanel(new BorderLayout());
		mainBoardPanel.setPreferredSize(new Dimension(472, 400));
		mainBoardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		content.add(mainBoardPanel);
		
		// Add GameBoard panel to content panel
		JPanel gridPanel = new JPanel(new GridBagLayout());
		//gridPanel.setMaximumSize(new Dimension(472, 402));
		GridBagConstraints c = new GridBagConstraints();
		gridPanel.setBackground(baseBlue);
		mainBoardPanel.add(gridPanel, BorderLayout.CENTER);
		Gameboard gameboard = new Gameboard();

		gameboard.generateTileGrid(gridPanel, c);
		
		
		//var boardLabelPanel = Box.createHorizontalBox();
		var boardLabelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
		boardLabelPanel.setBackground(baseBlue);
		//boardLabelPanel.setMaximumSize(new Dimension(182*2, 60));
		var leftLabelPanel = new JPanel();
		leftLabelPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		leftLabelPanel.setPreferredSize(new Dimension(182, 46));
		leftLabelPanel.setBackground(baseBlue);
		var leftLabel = new JLabel("Connect 4!", JLabel.CENTER);
		leftLabel.setFont(new Font("arial", Font.ITALIC, 24));
		leftLabelPanel.add(leftLabel);
		var rightLabelPanel = new JPanel();
		rightLabelPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		rightLabelPanel.setPreferredSize(new Dimension(182, 46));
		rightLabelPanel.setBackground(baseBlue);
		var rightLabel = new JLabel("Player1's Turn", JLabel.CENTER);
		rightLabel.setFont(new Font("arial", Font.ITALIC, 24));
		rightLabelPanel.add(rightLabel);		
		boardLabelPanel.add(leftLabelPanel);
		boardLabelPanel.add(rightLabelPanel);
		mainBoardPanel.add(boardLabelPanel, BorderLayout.NORTH);
		
		// Add GameInfo to right panel group
		var rightPanelGroup = Box.createVerticalBox();
		GameInfo gi = new GameInfo();
		gi.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		gi.setPreferredSize(new Dimension(216, 98));
		rightPanelGroup.add(gi);
		
		// Add Chat panel to content panel
		var chatPanel = new ChatBoxContentPane();
		rightPanelGroup.add(chatPanel);
		content.add(rightPanelGroup);
		
		setVisible(true);
		pack();
		
		
		
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
