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

import javax.swing.JLabel;

import connectfour.model.timer.ControllableTimerChangeListener;

/**
 * A Label intended to respond to timer changes are display formatted time
 */
public class TimerDisplayLabel extends JLabel implements ControllableTimerChangeListener {
	
	/**
	 * Default serial version ID, used when deserializing
	 */
	private static final long serialVersionUID = 1L;
	
	/** The base text to use when setting time. Text will be baseText + time */
	private String baseText;

	/**
	 * Create a new TimerDisplayLabel with the given text.
	 * @param text Display text
	 */
	public TimerDisplayLabel(String text) {
		super(text);
		baseText = text;
	}
	
	/**
	 * Changes the base text that this label displays
	 * @param text The new base text
	 */
	public void setBaseText(String text) {
		baseText = text;
	}

	/**
	 * Updates the current time display
	 */
	@Override
	public void setTime(int time) {
		int minutes = time / 60;
		int seconds = time % 60;
		this.setText(baseText + String.format("%d:%02d", minutes, seconds));
		//System.out.println(String.format("%d:%d", minutes, seconds));
	}

}
