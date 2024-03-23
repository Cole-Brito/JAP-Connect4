package connectfour.model.locale;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LanguageSet{
	private Map<String, String> keywords = new HashMap();
	
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
	
	public String getKeyword(String key) {
		return keywords.get(key);
	}
}
