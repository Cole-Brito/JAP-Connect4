/**
 * Connect4
 * Authors: Cole Brito, Paul Squires 
 * Section: 301
 * Professor: Daniel Cormier
 * Last Modified: March 24, 2024
 * Algonquin College CET-CS
 * JAP - Assignment 2-2
 */

package connectfour.model.locale;

/**
 * Interface to implement on a class that responds to changes to the locale
 */
public interface LocaleChangeListener {
	
	/**
	 * Classes must implement this method to update text to use the new language.
	 * @param newLanguage The new LanguageSet to use.
	 */
	public void onLocaleChanged(LanguageSet newLanguage);
}
