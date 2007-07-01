package org.australia.tests;

import java.util.Random;

public class RandomTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// 100 000 randoms
		int iterations = 100000000;
		
		long start = System.currentTimeMillis();
		for(int i=0; i< iterations; i++){
			Math.random();
		}
		System.out.println("Math.random(): \t" + (System.currentTimeMillis() - start));
		
		start = System.currentTimeMillis();
		Random random = new Random();
		for(int i=0; i< iterations; i++){
			random.nextBoolean();
		}
		System.out.println("Random(): \t" + (System.currentTimeMillis() - start));

	}

}
