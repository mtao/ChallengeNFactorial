package com.mtao.challengenfactorial;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	/**
	 * @param args
	 */
	
	static String prompt(String p) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = null;
		while(line == null) {
			try {
				System.out.print(p);
				line = br.readLine();
			} catch (IOException ioe) {
			}
		}
		return line;
	}
	public static void main(String[] args) {
		


		int val = -1;
		while(val == -1) {
			try {
				val = Integer.parseInt(prompt("How many cards?"));
			} catch (NumberFormatException e) {
				
			}
		}
		
		while(!(prompt("Hit?")).equals("no")) {
		CardFactory factory = new CardFactory(val);
		Card c = factory.genCard();
		CardSolver solver = new CardSolver(c);
		System.out.println(Equation.toString(solver.solutions.get(0),c));
		}

	}

}
