package test;

import org.junit.Test;

import com.test.autocorrect.core.Autocorrector;

import junit.framework.Assert;

public class UnitTest {

	private Autocorrector ac = new Autocorrector();
	
	@Test
	public void testExtraWord(){
		String input = "teest";
		String suggestion = ac.suggest(input);
		Assert.assertTrue(suggestion.equals("test"));
	}
	
	@Test
	public void testMissingWord(){
		String input = "hom";
		String suggestion = ac.suggest(input);
		Assert.assertTrue(suggestion.equals("home"));
	}
	
	@Test
	public void testPermutation(){
		String input = "godo";
		String suggestion = ac.suggest(input);
		Assert.assertTrue(suggestion.equals("good"));
	}
}
