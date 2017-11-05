package com.test.autocorrect.core;

import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.test.autocorrect.util.DictionaryUtil;

/**
 * This class contains the logic for String validation and applying strategy for auto-correcting the given input word
 * 
 * @author irfan
 *
 */
public class Autocorrector {

	private Logger logger = Logger.getLogger(Autocorrector.class.getName());
	
	private Pattern pattern = Pattern.compile("([a-zA-z])+");
	private static Set<String> DICTIONARY;
	private AutoCorrectStrategy strategy;

	public Autocorrector() {
		DICTIONARY = DictionaryUtil.getDictionary();
		strategy = new AutoCorrectStrategy();
	}

	/**
	 * This method takes the input word and return corrected matched word if the
	 * given word is not valid word as per dictionary
	 * 
	 * @param input
	 *            given word
	 * @return auto corrected word or same input if it is a valid word
	 */
	public String suggest(String input) {
		String suggestion = "";

		if (input != null && input.trim().length() > 0) {
			input = input.toLowerCase(); // Handling all words as lower case for String equality

			if (DICTIONARY.contains(input)) {
				logger.info("Word \"" + input + "\" is already present in dictionary. Returning same word back.");
				return input;
			}
			boolean isValidStr = pattern.matcher(input).matches(); // Check for valid English alphabets

			if (isValidStr) {
				if (input.length() < 3) { // process input with atleast 3 letters
					logger.info("Alphabet \"" + input + "\" is too short for generating suggestion, enter more letters.");
					return input;
				}
					// Order of checking to get most relevant suggestion
					
					//#1 1st check - Check for extra char
					suggestion = strategy.checkForExtraChar(input);
					
					//#2 2nd check - Check for missing char, assuming missing char is vowel to increase match relevance
					suggestion = suggestion == "" ? strategy.checkForMissingChar(input, true) : suggestion;  // check for missing vowels
					
					//#3 3rd check - Check for missing char, assuming missing char is consonant
					suggestion = suggestion == "" ? strategy.checkForMissingChar(input, false) : suggestion; // check for missing consonants
					
					//#4 4th check - Check for any permutation
					suggestion =  suggestion == "" ? strategy.checkPermutation(input) : suggestion;

			} else {
				logger.info("Received invalid alphabetic input \"" + input + "\"");
				return input;
			}
		} else {
			logger.info("Received empty or invalid input...!");
			return input;
		}

		if (!"".equals(suggestion))
			logger.info("Word \"" + input + "\" is auto-corrected as \"" + suggestion + "\"");
		else
			logger.info("No suggestion found for word \"" + input + "\"");

		return suggestion;
	}
}
