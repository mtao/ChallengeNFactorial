package com.mtao.challengenfactorial;
import java.util.Random;
public class CardFactory {

	int factorial(int i) {
		if(i>0) {
			return i * factorial(i-1);
		} else {
			return 1;
		}
	}
	CardFactory(int size_) {
		size = size_;
		targetValue = factorial(size);
	}
	
	Card genCard() {
		Card c = new Card(size);
		Random rng = new Random();
		do {
			for(int i=0; i < size; ++i) {
				c.set(i,rng.nextInt(30)+1);
			}
			
		} while(!c.hasSolution());

		for(int i=0; i < size; ++i) {
			System.out.print(c.get(i) + " ");
		}
		System.out.println();
		return c;
	}
	
	static int size=4;
	static int targetValue=24;

}
