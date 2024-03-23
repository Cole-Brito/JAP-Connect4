package connectfour.model.locale;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

//Name Pending
public class LocaleManager {
	
	private static LocaleManager _instance;
	public static LocaleManager getInstance() {
		if (_instance == null) {
			_instance = new LocaleManager();
		}
		return _instance;
	}
	
	private final Pattern languageFilePattern = Pattern.compile("\"(?<key>[\\w.]+)\"=\"(?<value>[\\p{IsLatin}.\\s]+)\"");
		
	private Map<String, LanguageSet> languageSets;
	
	private LanguageSet activeLanguageSet;
	
	private Set<LocaleChangeListener> localeChangeListeners;
	
	private LocaleManager() {
		languageSets = new HashMap<String, LanguageSet>();
		localeChangeListeners = new HashSet<>();
	}
	
	public LanguageSet getActiveLanguageSet() {
		return activeLanguageSet;
	}
	
	public LanguageSet setActiveLanguageSet(String languageName) {
		LanguageSet set = languageSets.get(languageName);
		if (set != null && set != activeLanguageSet) {
			activeLanguageSet = set;
		}
		
		return activeLanguageSet;
	}
	
	public String getKeywordFromActiveLanguage(String key) {
		if (activeLanguageSet != null) {
			String keyword = activeLanguageSet.getKeyword(key);
			if (keyword != null) {
				return keyword;
			}
		}
		return "[Error: Key Missing]";
	}
	
	public boolean loadLanguageSet(String fileName) {
		try (Scanner file = new Scanner(LocaleManager.class.getResourceAsStream(fileName))) {
			LanguageSet language = new LanguageSet();
			//file.useDelimiter(";");
			
			// Assume the file starts with the language name
			String languageName = file.nextLine();
			if (!languageName.isBlank()) {
				// Remove quotes from language name
				languageName = languageName.replace("\s", "");
				
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
	
	public void registerLocaleChangeListener(LocaleChangeListener listener) {
		localeChangeListeners.add(listener);
	}
	
	public void unRegisterLocaleChangeListener(LocaleChangeListener listener) {
		localeChangeListeners.remove(listener);
	}
	
	public void notifyLocaleChangeListeners(LanguageSet newLanguage) {
		for(var listener: localeChangeListeners) {
			listener.onLocaleChanged(newLanguage);
		}
	}
}
