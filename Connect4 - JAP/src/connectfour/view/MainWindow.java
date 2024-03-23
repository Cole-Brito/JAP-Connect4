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

import connectfour.controller.GameController;

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
	
	public final GameBoardPanel gameBoardPanel;
	
	public final GameInfoPanel gameInfoPanel;
	
	public final ChatHistoryTextPane chatHistoryTextPane;
	
	public final ChatTextInputField chatTextInputField;
	
	private final JLabel leftLabel;
	private final JLabel rightLabel;
		
	/**
	 * 
	 */
	public MainWindow() {
		super("Connect 4");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		//690 x 430 was what was on our draft but seems small now
		setMinimumSize(new Dimension(690,430));
		//Creating the menu bar
		generateMenuBar();
		//TODO: Move to GUI constants/styles class
		//Used for content bg
		
		
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
		gridPanel.setBackground(GameWindowStyles.uiBackgroundBase);
		mainBoardPanel.add(gridPanel, BorderLayout.CENTER);
		gameBoardPanel = new GameBoardPanel();
		gameBoardPanel.generateTileGrid(gridPanel, c);
		
		
		//var boardLabelPanel = Box.createHorizontalBox();
		var boardLabelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
		boardLabelPanel.setBackground(GameWindowStyles.uiBackgroundBase);
		//boardLabelPanel.setMaximumSize(new Dimension(182*2, 60));
		var leftLabelPanel = new JPanel();
		leftLabelPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		leftLabelPanel.setPreferredSize(new Dimension(182, 46));
		leftLabelPanel.setBackground(GameWindowStyles.uiBackgroundBase);
		// Setting leftLabel reference
		leftLabel = new JLabel("Connect 4!", JLabel.CENTER);
		leftLabel.setFont(new Font("arial", Font.ITALIC, 24));
		leftLabelPanel.add(leftLabel);
		var rightLabelPanel = new JPanel();
		rightLabelPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		rightLabelPanel.setPreferredSize(new Dimension(182, 46));
		rightLabelPanel.setBackground(GameWindowStyles.uiBackgroundBase);
		// Setting rightLabel reference
		rightLabel = new JLabel("Player1's Turn", JLabel.CENTER);
		rightLabel.setFont(new Font("arial", Font.ITALIC, 24));
		rightLabelPanel.add(rightLabel);		
		boardLabelPanel.add(leftLabelPanel);
		boardLabelPanel.add(rightLabelPanel);
		mainBoardPanel.add(boardLabelPanel, BorderLayout.NORTH);
		
		// Add GameInfo to right panel group
		var rightPanelGroup = Box.createVerticalBox();
		gameInfoPanel = new GameInfoPanel();
		gameInfoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		gameInfoPanel.setPreferredSize(new Dimension(216, 98));
		rightPanelGroup.add(gameInfoPanel);
		
		// Add ChatHistoryTextPane to right panel group
		JPanel chatHistoryPanel = new JPanel();
		chatHistoryPanel.setPreferredSize(new Dimension(216, 246));
		chatHistoryPanel.setBackground(GameWindowStyles.uiBackgroundLight);
		chatHistoryPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		chatHistoryTextPane = new ChatHistoryTextPane();
		JScrollPane scrollPane = new JScrollPane(chatHistoryTextPane);
		scrollPane.setPreferredSize(new Dimension(190, 234));
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		chatHistoryPanel.add(scrollPane);
		//TODO: TextPane doesn't seem to use background color
		chatHistoryPanel.setBackground(GameWindowStyles.uiBackgroundMed);
		rightPanelGroup.add(chatHistoryPanel, BorderLayout.CENTER);
		
		// Add ChatTextInputField to right panel group
		JPanel textInputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
		//textInputPanel.setMinimumSize(new Dimension(218, 55));
		textInputPanel.setPreferredSize(new Dimension(216, 55));
		textInputPanel.setBackground(GameWindowStyles.uiBackgroundLight);
		textInputPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		chatTextInputField = new ChatTextInputField();
		chatTextInputField.setBackground(GameWindowStyles.uiBackgroundLight);
		textInputPanel.add(chatTextInputField);
		rightPanelGroup.add(textInputPanel);
		
		// Add the right panel group to MainWindow
		content.add(rightPanelGroup);
		
		// pack to have elements adjust size to fit contents, then make sure window is visible
		pack();
		setVisible(true);
				
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
		JMenuItem frenchItem = new JMenuItem("Français", null);
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
		JMenuItem accessItem = new JMenuItem("Accessiblity", null);
		viewMenu.add(accessItem);
		
		//Setting Mnemonics
		fileMenu.setMnemonic('F');
		gameMenu.setMnemonic('G');
		langMenu.setMnemonic('L');
		viewMenu.setMnemonic('V');
		helpMenu.setMnemonic('H');
		networkMenu.setMnemonic('N');
		
//		loadItem.addActionListener();
		
		//Centering the window
		setLocationRelativeTo(null);
	}
	
}
