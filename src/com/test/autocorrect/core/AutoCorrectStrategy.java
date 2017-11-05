package com.test.autocorrect.core;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import com.test.autocorrect.util.DictionaryUtil;

/** This class contains various auto-correct strategies
 * @author irfan
 *
 */
public class AutoCorrectStrategy {

	private static Set<String> DICTIONARY;
	private Logger logger = Logger.getLogger(AutoCorrectStrategy.class.getName());
	
	
	private static final Set<String> APLHABETS = new HashSet<>(21); // consonants
	private static final Set<String> VOWELS = new HashSet<>(5);
	
	public AutoCorrectStrategy() {
		DICTIONARY = DictionaryUtil.getDictionary();
		loadAlphabets();
	}
	
	private void loadAlphabets() {
		for (char c = 'a'; c <= 'z'; c++) {
			boolean vowel = c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
			if (vowel) {
				VOWELS.add(String.valueOf(c));
			} else
				APLHABETS.add(String.valueOf(c));
		}
	}
	
	 /** Check if removing one extra char at any position gives valid suggestion
	 * @param input word to correct
	 * @return corrected word if found or blank string if not found in dictionary
	 */
	String checkForExtraChar(String input) {
		int len = input.length();

		// Check from last assuming most user start with correct letters and
		// then make mistake
		for (int i = len - 1; i >= 0; i--) {
			StringBuilder suggestion = new StringBuilder();

			// Generate suggestion by removing one char at a time
			suggestion.append(input.substring(0, i)).append(input.substring(i + 1));

			if (DICTIONARY.contains(suggestion.toString())){
				logger.info("Strategy used : Removed an extra char");
				return suggestion.toString();
			}
		}
		return "";
	}

	/**
	 * Check if adding one extra alphabet gives valid suggestion
	 * 
	 * @param input
	 *            input word to check
	 * @param checkVowels
	 *            if true check vowels only, if false check for consonants
	 * @return
	 */
	 String checkForMissingChar(String input, boolean checkVowels) {
		int len = input.length();

		for (int i = len - 1; i >= 0; i--) {
			for (String az : checkVowels ? VOWELS : APLHABETS) {
				StringBuilder suggestion = new StringBuilder();

				// Generate suggestion by adding one char at a time from end to
				// start of string
				suggestion.append(input.substring(0, i + 1)).append(az).append(input.substring(i + 1));

				if (DICTIONARY.contains(suggestion.toString())){
					logger.info("Strategy used : Added a missing char");
					return suggestion.toString();
				}
			}
		}
		return "";
	 }
	 
	 /** This method checks if any permutation of given word is available in dictionary
		 * @param input word to correct
		 * @return matched permutation
		 */
		 String checkPermutation(String input) {
			 String s =  checkPermutation("", input); 
			 if(!"".equals(s))
					logger.info("Strategy used : Permutation ");
			 return s;
		}
		 
		    private String checkPermutation(String fixedPart, String input) {
		        int len = input.length();
		        if (len == 0) {
		        	if (DICTIONARY.contains(fixedPart))
						return fixedPart;
		        }
		        else {
		            for (int i = 0; i < len; i++) {
		            	//Generate all permutations and check if present.
		            	//For greater relevance, generate permutation from end of string
						String str = checkPermutation(fixedPart + input.charAt(i), input.substring(0, i) + input.substring(i+1, len));
						
						if(!"".equals(str)){ // found matching prefix
		            		 return str;
						}
					}
		        }
				return "";
		    }
}
