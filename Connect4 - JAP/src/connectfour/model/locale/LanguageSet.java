/**
 * Connect4
 * Authors: Cole Brito, Paul Squires 
 * Section: 301
 * Professor: Daniel Cormier
 * Last Modified: April 7, 2024
 * Algonquin College CET-CS
 * JAP - Assignment 3-2
 */

package connectfour.model.locale;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Stores a dictionary of Language keywords
 */
public class LanguageSet{
	/** Map of language key and keywords */
	private Map<String, String> keywords = new HashMap<>();
	
	/**
	 * Gets a map of all keyword in this language set
	 * @return readonly map of keywords
	 */
	public Map<String, String> getKeywords(){
		return Collections.unmodifiableMap(keywords);
	}
	
	/**
	 * Adds a new keyword to this LanguageSet.
	 * If the key already exists, it will overwrite the previous keyword.
	 * @param key The key for the new keyword
	 * @param value The keyword corresponding to the given key
	 */
	public void addKeyword(String key, String value) {
		keywords.put(key, value);
	}
	
	/**
	 * Get a keyword for a given key
	 * @param key The key to use to lookup the keyword
	 * @return The keyword for the given key. May be null
	 */
	public String getKeyword(String key) {
		return keywords.get(key);
	}
}
