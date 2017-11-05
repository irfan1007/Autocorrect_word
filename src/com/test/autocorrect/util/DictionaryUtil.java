package com.test.autocorrect.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

/** This class loads the dictionary of words from the file specified in resources folder 
 * @author irfan
 *
 */
public class DictionaryUtil {

	private static final String WORDS_FILE = "./resources/dictionary.txt";
	private static Set<String> DICT = new HashSet<>(1000);
	private static Logger LOGGER = Logger.getLogger(DictionaryUtil.class.getName());

	static {
		loadDictionary();
	}

	/**
	 * @return The populated dictionary
	 */
	public static Set<String> getDictionary() {
		return Collections.unmodifiableSet(DICT);
	}

	private static void loadDictionary() {
		Properties properties = new Properties();
		try {
			File file = new File(WORDS_FILE);
			FileInputStream in = new FileInputStream(file);
			BufferedInputStream inStream = new BufferedInputStream(in);
			properties.load(inStream);
			for(Object key : properties.keySet()){
				String word = key.toString();
				word = word.toLowerCase();
				DICT.add(word);
			}
		} catch (FileNotFoundException e) {
			LOGGER.severe( "Words file not found" + e);
		} catch (IOException e) {
			LOGGER.severe("IO exception while loading word file" + e);
		}
		LOGGER.info("Loaded words from given dictionary");
	}
}
