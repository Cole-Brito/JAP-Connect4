import javax.swing.*;
import java.awt.*;

public class View extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8215973777232473220L;

	View() {
		super("Connect 4");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setMinimumSize(new Dimension(690,430));
		pack();
		setVisible(true);
		
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
		
	} //end of view
	
	/*
	 * Note: Just made this for practice really, we were going do mouse events
	 * for the play space. Will change this to display the tiles instead
	 */
	private void generateTileGrid(JPanel content, GridBagConstraints c) {
		//Grid of buttons 
		JButton[][] buttons =  new JButton[6][7];
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 7; j++) {
					buttons[i][j] = new JButton("Btn " + i + "-" + j);
					c.gridx = j;
					c.gridy = i;
					content.add(buttons[i][j], c);
				}
			}
	}
	
}
