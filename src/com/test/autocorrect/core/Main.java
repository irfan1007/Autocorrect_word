package com.test.autocorrect.core;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Autocorrector autocorrector = new Autocorrector();
		System.out.print("Enter word : ");
		
		Scanner sc = new Scanner(System.in);
		String word = sc.nextLine();
		System.out.println("Given input => " + word);
		
		String suggest = autocorrector.suggest(word);
		System.out.println("Suggested word => " + suggest);
		sc.close();
	}

}
