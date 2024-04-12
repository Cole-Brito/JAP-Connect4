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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Loads and stores a map of LanguageSet mapped to language names.
 * Can notify listeners when locale changes.
 */
public class LocaleManager {
	
	/** The singleton instance of LocaleManager */
	private static LocaleManager _instance;
	/**
	 * Gets the singleton instance of LocaleManager.
	 * Constructs a new LocaleManager only the first time it is called.
	 * @return The instance of LocaleManager
	 */
	public static LocaleManager getInstance() {
		if (_instance == null) {
			_instance = new LocaleManager();
		}
		return _instance;
	}
	
	/** The pattern to use for individual keyword lines in the locale file */
	private final Pattern languageFilePattern = Pattern.compile("\"(?<key>[\\w.]+)\"=\"(?<value>[^\"]+)\"");
		
	/** The map of language keys and LanguageSet values */
	private Map<String, LanguageSet> languageSets;
	
	/** The currently active language set */
	private LanguageSet activeLanguageSet;
	
	/** A collection of LocalChangeListeners to respond to locale changes */
	private Set<LocaleChangeListener> localeChangeListeners;
	
	/**
	 * Private constructor for LocaleManager singleton
	 */
	private LocaleManager() {
		languageSets = new HashMap<String, LanguageSet>();
		localeChangeListeners = new HashSet<>();
	}
	
	/**
	 * Gets the currently active language set
	 * @return active LanguageSet
	 */
	public LanguageSet getActiveLanguageSet() {
		return activeLanguageSet;
	}
	
	/**
	 * Sets the active language set
	 * @param languageName The key (set name) for the language set
	 * @return The active LanguageSet, which may be the same as previous if not changed.
	 */
	public LanguageSet setActiveLanguageSet(String languageName) {
		LanguageSet set = languageSets.get(languageName);
		if (set != null && set != activeLanguageSet) {
			activeLanguageSet = set;
			notifyLocaleChangeListeners(activeLanguageSet);
		}
		
		return activeLanguageSet;
	}
	
	/**
	 * Gets a keyword from the active LanguageSet, by key
	 * @param key The key for the language keyword
	 * @return The keyword, or "[Error: Key Missing]".
	 */
	public String getKeywordFromActiveLanguage(String key) {
		if (activeLanguageSet != null) {
			String keyword = activeLanguageSet.getKeyword(key);
			if (keyword != null) {
				return keyword;
			}
		}
		return "[Error: Key Missing]";
	}
	
	/**
	 * Reads a LanguageSet from a file
	 * @param fileName The name of the file to read
	 * @return true if the LanguageSet was read successfully, false otherwise
	 */
	public boolean loadLanguageSet(String fileName) {
		try (Scanner file = new Scanner(LocaleManager.class.getResourceAsStream(fileName), StandardCharsets.UTF_8)) {
			LanguageSet language = new LanguageSet();
			//file.useDelimiter(";");
			
			// Assume the file starts with the language name
			String languageName = file.nextLine();
			if (!languageName.isBlank()) {
				// Remove quotes from language name
				languageName = languageName.replace("\"", "");
				
				while(file.hasNextLine()) {
					String nextLine = file.nextLine();
					var matcher = languageFilePattern.matcher(nextLine);
					if (matcher.matches()) {
						language.addKeyword(matcher.group("key"), matcher.group("value"));
					}
					else {
						System.err.println("Line in LanguageSet file did not match regex: " + nextLine);
					}
				}

				languageSets.put(languageName, language);
			}
			
			if (activeLanguageSet == null) {
				activeLanguageSet = language;
			}

			return true;
		}
		catch(NullPointerException e) {
			System.err.println("File " + fileName + " does not exist");
		}
		catch(Exception e) {
			System.err.println(e.getMessage());
		}
		
		return false;
	}
	
	/**
	 * Adds a listener to respond to locale changes
	 * @param listener The LocaleChangeListener
	 */
	public void registerLocaleChangeListener(LocaleChangeListener listener) {
		localeChangeListeners.add(listener);
	}
	
	/**
	 * Removes a registered LocaleChangeListener
	 * @param listener The LocaleChangeListener to remove
	 */
	public void unRegisterLocaleChangeListener(LocaleChangeListener listener) {
		localeChangeListeners.remove(listener);
	}
	
	/**
	 * Notifies all registered listeners when the locale changes
	 * @param newLanguage The new LanguageSet for the locale
	 */
	public void notifyLocaleChangeListeners(LanguageSet newLanguage) {
		for(var listener: localeChangeListeners) {
			listener.onLocaleChanged(newLanguage);
		}
	}
}
