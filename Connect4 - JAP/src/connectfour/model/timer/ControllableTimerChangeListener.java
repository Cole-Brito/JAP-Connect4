/**
 * Connect4
 * Authors: Cole Brito, Paul Squires 
 * Section: 301
 * Professor: Daniel Cormier
 * Last Modified: April 7, 2024
 * Algonquin College CET-CS
 * JAP - Assignment 3-2
 */

package connectfour.model.timer;

/**
 * Implement on a class that responds to updates from a ControllableTime
 */
public interface ControllableTimerChangeListener {
	
	/**
	 * Updates this listener with a new time.
	 * @param time The new time
	 */
	public void setTime(int time);
}
