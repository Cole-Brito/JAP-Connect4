package view;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8215973777232473220L;

	public MainWindow() {
		super("Connect 4");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//690 x 430 was what was on our draft but seems small now
		setMinimumSize(new Dimension(690,430));
		pack();
		setVisible(true);
		
		//Adding the menu bar to the window
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		//Menu items
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		JMenu gameMenu = new JMenu("Game");
		menuBar.add(gameMenu);
		JMenu networkMenu = new JMenu("Network");
		menuBar.add(networkMenu);
		JMenu langMenu = new JMenu("Language");
		menuBar.add(langMenu);
		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		JMenu viewMenu = new JMenu("View");
		menuBar.add(viewMenu);
		
		JPanel content = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		add(content);
		
		generateTileGrid(content, c);
		
		/*
		 * Buttons to test gridbaglayout
		 */
//		JButton bTest1, bTest2, bTest3, bTest4;
//		
//		bTest1 = new JButton("btn1");
//		c.gridx = 0;
//		c.gridy = 0;
//		content.add(bTest1, c);
//		
//		bTest2 = new JButton("btn2");
//		c.gridx = 1;
//		c.gridy = 3;
//		content.add(bTest2, c);
//		
//		bTest3 = new JButton("btn3");
//		c.gridx = 2;
//		c.gridy = 2;
//		content.add(bTest3, c);
//		
//		bTest4 = new JButton("btn4");
//		c.gridx = 3;
//		c.gridy = 1;
//		content.add(bTest4, c);
		
	} //end of main window
	
	/*
	 * Note: Just made this for practice really, we were going do mouse events
	 * for the play space. Will change this to display the tiles instead
	 */
	private void generateTileGrid(JPanel content, GridBagConstraints c) {
		//Grid of buttons 
		JLabel[][] tile = new JLabel[6][7];
		ImageIcon emptyTile = new ImageIcon(getClass().getResource("/Connect4Assets/emptyTile.png"));	
		
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 7; j++) {
					tile[i][j] = new JLabel(emptyTile);
					c.gridx = j;
					c.gridy = i;
					content.add(tile[i][j], c);
				}
			}
	}
	
}
