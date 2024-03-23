package connectfour.model.locale;

public interface LocaleChangeListener {
	
	/**
	 * Classes must implement this method to update text to use the new language.
	 * @param newLanguage The new LanguageSet to use.
	 */
	public void onLocaleChanged(LanguageSet newLanguage);
}
