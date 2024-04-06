/**
 * Connect4
 * Authors: Cole Brito, Paul Squires 
 * Section: 301
 * Professor: Daniel Cormier
 * Last Modified: March 24, 2024
 * Algonquin College CET-CS
 * JAP - Assignment 2-2
 */

package connectfour.view;
import javax.swing.*;

import connectfour.model.GameManager;
import connectfour.model.GameState;
import connectfour.model.locale.LanguageSet;
import connectfour.model.locale.LocaleChangeListener;
import connectfour.model.locale.LocaleManager;

import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The main UI window, containing all other UI elements for the game.
 */
public class MainWindow extends JFrame implements PropertyChangeListener, LocaleChangeListener {

	/**
	 * Serial Version UID, used to compare class versions when deserializing
	 */
	private static final long serialVersionUID = 1L;
	
	/** Stored reference of the GameBoardPanel */
	public final GameBoardPanel gameBoardPanel;
	
	/** Stored reference of the GameInfoPanel */
	public final GameInfoPanel gameInfoPanel;
	
	/** Stored reference of the ChatHistoryTextPane */
	public final ChatHistoryTextPane chatHistoryTextPane;
	
	/** Stored reference of the ChatTextInputField */
	public final ChatTextInputField chatTextInputField;
	
	/** The left game panel showing the game name */
	private final JLabel leftLabel;
	/** The right game panel showing the game state */
	private final JLabel rightLabel;
	
	/** A map of menu items to respond to locale changes, key is LanguageSet key */
	private Map<String, AbstractButton> localeResponsiveMenuItems = new HashMap<>();
	
	/**File Menu*/
	JMenu fileMenu;
	/**Save Menu Item*/
	JMenuItem saveItem;
	/**Load Menu Item*/
	JMenuItem loadItem;
	/**Game Menu*/
	JMenu gameMenu;
	/**Restart Menu Item*/
	JMenuItem restartItem;
	/**Player List Menu Item*/
	JMenuItem playerItem;
	/**Game Mode Menu Item*/
	JMenuItem modeItem;
	/**Network Menu*/
	JMenu networkMenu;
	/**Host Menu Item*/
	JMenuItem hostItem;
	/**Connect Menu Item*/
	JMenuItem connectItem;
	/**Disconnect Menu Item*/
	JMenuItem disconnectItem;
	/**Language Menu*/
	JMenu langMenu;
	/**English Menu Item*/
	JMenuItem englishItem;
	/**French Menu Item*/
	JMenuItem frenchItem;
	/**Help Menu*/
	JMenu helpMenu;
	/**How to Play Menu Item*/
	JMenuItem controlsItem;
	/**About Menu Item*/
	JMenuItem aboutItem;
	/**View Menu*/
	JMenu viewMenu;
	/**Theme Menu Item*/
	JMenuItem themeItem;
	/**Accessibility Menu Item*/
	JMenuItem accessItem;
		
	/**
	 * Constructs the MainWindow and all child UI elements
	 */
	public MainWindow() {
		super("Connect 4");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
		//Centering the window
		setLocationRelativeTo(null);
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
		fileMenu = addLocaleMenu("FileMenu.File");
		menuBar.add(fileMenu);
		saveItem = addLocaleMenuItem("FileMenu.Save");
		fileMenu.add(saveItem);
		loadItem = addLocaleMenuItem("FileMenu.Load");
		fileMenu.add(loadItem);
		//Game menu and options
		gameMenu = addLocaleMenu("GameMenu.Game");
		menuBar.add(gameMenu);
		restartItem = addLocaleMenuItem("GameMenu.Restart");
		gameMenu.add(restartItem);
		playerItem = addLocaleMenuItem("GameMenu.PlayerList");
		gameMenu.add(playerItem);
		modeItem = addLocaleMenuItem("GameMenu.Gamemode");
		gameMenu.add(modeItem);
		//Network menu and options
		networkMenu = addLocaleMenu("NetworkMenu.Network");
		menuBar.add(networkMenu);
		hostItem = addLocaleMenuItem("NetworkMenu.Host");
		networkMenu.add(hostItem);
		connectItem = addLocaleMenuItem("NetworkMenu.Connect");
		networkMenu.add(connectItem);
		disconnectItem = addLocaleMenuItem("NetworkMenu.Disconnect");
		networkMenu.add(disconnectItem);
		//Language menu and options
		langMenu = addLocaleMenu("LanguageMenu.Language");
		menuBar.add(langMenu);
		englishItem = addLocaleMenuItem("LanguageMenu.English");
		langMenu.add(englishItem);
		frenchItem = addLocaleMenuItem("LanguageMenu.French");
		langMenu.add(frenchItem);
		//Help menu and options
		helpMenu = addLocaleMenu("HelpMenu.Help");
		menuBar.add(helpMenu);
		controlsItem = addLocaleMenuItem("HelpMenu.HowToPlay");
		helpMenu.add(controlsItem);
		aboutItem = addLocaleMenuItem("HelpMenu.About");
		helpMenu.add(aboutItem);
		//View menu and options
		viewMenu = addLocaleMenu("ViewMenu.View");
		menuBar.add(viewMenu);
		themeItem = addLocaleMenuItem("ViewMenu.Theme");
		viewMenu.add(themeItem);
		accessItem = addLocaleMenuItem("ViewMenu.Accessibility");
		viewMenu.add(accessItem);
		
		//Setting Mnemonics
		fileMenu.setMnemonic('F');
		gameMenu.setMnemonic('G');
		langMenu.setMnemonic('L');
		viewMenu.setMnemonic('V');
		helpMenu.setMnemonic('H');
		networkMenu.setMnemonic('N');
		
		loadItem.setActionCommand("load");
		saveItem.setActionCommand("save");
		restartItem.setActionCommand("restart");
		playerItem.setActionCommand("playerlist");
		modeItem.setActionCommand("mode");
		hostItem.setActionCommand("host");
		connectItem.setActionCommand("connect");
		disconnectItem.setActionCommand("disconnect");
		englishItem.setActionCommand("english");
		frenchItem.setActionCommand("french");
		controlsItem.setActionCommand("controls");
		aboutItem.setActionCommand("about");
		themeItem.setActionCommand("theme");
		accessItem.setActionCommand("access");
	}
	
	/**
	 * Creates a JMenu and adds to the list of elements that respond to locale changes
	 * @param localeKey The key to the language keyword to use for this element
	 * @return The newly created JMenu
	 */
	private JMenu addLocaleMenu(String localeKey) {
		JMenu menu = new JMenu(LocaleManager.getInstance().getKeywordFromActiveLanguage(localeKey));
		localeResponsiveMenuItems.put(localeKey, menu);
		return menu;
	}
	
	/**
	 * Creates a JMenuItem and adds to the list of elements that respond to locale changes
	 * @param localeKey The key to the language keyword to use for this element
	 * @return The newly created JMenuItem
	 */
	private JMenuItem addLocaleMenuItem(String localeKey) {
		JMenuItem menuItem = new JMenuItem(LocaleManager.getInstance().getKeywordFromActiveLanguage(localeKey));
		localeResponsiveMenuItems.put(localeKey, menuItem);
		return menuItem;
	}

	/**
	 * Registers an ActionListener to respond to all Menu items
	 * @param listener The ActionListener to respond to menu items
	 */
	public void registerMenuListeners(ActionListener listener) {
		loadItem.addActionListener(listener);
		saveItem.addActionListener(listener);
		restartItem.addActionListener(listener);
		playerItem.addActionListener(listener);
		modeItem.addActionListener(listener);
		hostItem.addActionListener(listener);
		connectItem.addActionListener(listener);
		disconnectItem.addActionListener(listener);
		englishItem.addActionListener(listener);
		frenchItem.addActionListener(listener);
		controlsItem.addActionListener(listener);
		aboutItem.addActionListener(listener);
		themeItem.addActionListener(listener);
		accessItem.addActionListener(listener);
	}
	
	/**
	 * Updates the game label (rightLabel) with text based on the current GameState
	 * @param state The GameState to update the label for
	 */
	private void updateGameStateLabel(GameState state) {
		switch(state) {
		case DEFAULT:
			rightLabel.setText("Waiting...");
			break;
		case DRAW:
			rightLabel.setText("DRAW");
			break;
		case PLAYER_1_TURN:
			//TODO: Replace text with player's names
			rightLabel.setText("Player 1's Turn");
			break;
		case PLAYER_1_WIN:
			rightLabel.setText("PLAYER 1 WINS!");
			break;
		case PLAYER_2_TURN:
			rightLabel.setText("Player 2's Turn");
			break;
		case PLAYER_2_WIN:
			rightLabel.setText("PLAYER 2 WINS!");
			break;
		default:
			break;
		}
		
		
	}
	
	/**
	 * Displays the about dialog box
	 */
	public void displayAbout() {
		JOptionPane.showMessageDialog(null, 
				"This game is made by Paul Squires and Cole Brito, "
				+ "It was created for the Algonquin College Computer Engineering Technology Course (CET-CS)");
	}
	
	/**
	 * Displays the help dialog box
	 */
	public void displayControls() {
		JOptionPane.showMessageDialog(null, 
				"To play click on the column you wish to drop a piece!");
	}

	/**
	 * Updates the MainWindow menus to use the new language keywords
	 */
	@Override
	public void onLocaleChanged(LanguageSet newLanguage) {
		// This is kinda gross lol
		// ^ a little bit lmao
		for(var item: localeResponsiveMenuItems.entrySet()) {
			item.getValue().setText(newLanguage.getKeyword(item.getKey()));
		}
	}

	/**
	 * PropertyChange event that responds to Game State changes
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		if (evt.getPropertyName() == GameManager.GAME_STATE_PROPERTY_NAME) {
			updateGameStateLabel((GameState)evt.getNewValue());
		}
	}
}
