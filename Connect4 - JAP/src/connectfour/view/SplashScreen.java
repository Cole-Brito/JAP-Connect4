/**
 * Connect4
 * Authors: Cole Brito, Paul Squires 
 * Section: 301
 * Professor: Daniel Cormier
 * Last Modified: April 7, 2024
 * Algonquin College CET-CS
 * JAP - Assignment 3-2
 */

package connectfour.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Class for creating the splash screen - code adjusted from Daniel Corimer
 * @author Cole Brito
 *
 */
public class SplashScreen extends JFrame {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Create a new SplashScreen with the proper appearance.
	 */
	public SplashScreen() {
		JPanel content = (JPanel) getContentPane();
		
		content.setBackground(Color.BLACK);
		ImageIcon bg = new ImageIcon(getClass().getResource("/images/SplashScreen.png"));
		JLabel image = new JLabel(bg);
		content.add(image, BorderLayout.CENTER);
		content.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		setContentPane(content);
		
		setUndecorated(true);//Turning off OS controls
		pack();
		setLocationRelativeTo(null); //Centering Frame
		setVisible(true);
	}
}
