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
import connectfour.model.locale.LanguageSet;
import connectfour.model.locale.LocaleChangeListener;
import connectfour.model.locale.LocaleManager;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
/**
 * 
 * @author Cole Brito, Paul Squires
 *
 */
import java.util.Set;
public class MainWindow extends JFrame implements LocaleChangeListener {

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
	
	private Map<String, AbstractButton> localeResponsiveMenuItems = new HashMap();
	
	/**Menu Items**/
	JMenu fileMenu;
	JMenuItem saveItem;
	JMenuItem loadItem;
	JMenu gameMenu;
	JMenuItem restartItem;
	JMenuItem playerItem;
	JMenuItem modeItem;
	JMenu networkMenu;
	JMenuItem hostItem;
	JMenuItem connectItem;
	JMenuItem disconnectItem;
	JMenu langMenu;
	JMenuItem englishItem;
	JMenuItem frenchItem;
	JMenu helpMenu;
	JMenuItem controlsItem;
	JMenuItem aboutItem;
	JMenu viewMenu;
	JMenuItem themeItem;
	JMenuItem accessItem;
		
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
		modeItem = new JMenuItem("GameMenu.Gamemode");
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
		JMenuItem aboutItem = addLocaleMenuItem("HelpMenu.About");
		helpMenu.add(aboutItem);
		//View menu and options
		JMenu viewMenu = addLocaleMenu("ViewMenu.View");
		menuBar.add(viewMenu);
		JMenuItem themeItem = addLocaleMenuItem("ViewMenu.Theme");
		viewMenu.add(themeItem);
		JMenuItem accessItem = addLocaleMenuItem("ViewMenu.Accessibility");
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
	
	private JMenu addLocaleMenu(String localeKey) {
		JMenu menu = new JMenu(LocaleManager.getInstance().getKeywordFromActiveLanguage(localeKey));
		localeResponsiveMenuItems.put(localeKey, menu);
		return menu;
	}
	
	private JMenuItem addLocaleMenuItem(String localeKey) {
		JMenuItem menuItem = new JMenuItem(LocaleManager.getInstance().getKeywordFromActiveLanguage(localeKey));
		localeResponsiveMenuItems.put(localeKey, menuItem);
		return menuItem;
	}


	@Override
	public void onLocaleChanged(LanguageSet newLanguage) {
		// This is kinda gross lol
		for(var item: localeResponsiveMenuItems.entrySet()) {
			item.getValue().setText(newLanguage.getKeyword(item.getKey()));
		}
	}
	
	
}
