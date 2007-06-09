package org.australia.util;

import java.util.Iterator;
import java.util.SortedSet;

import org.australia.commons.Individual;

public class Utils {
	
	
	public static boolean[] getRandomPattern(int length){
		
		boolean[] pattern = new boolean[length];
		
		for (int i = 0; i < pattern.length; i++) {
			pattern[i] = Math.random() < 0.5;
		}
		
		return pattern;
		
	}

	public static int getRandomInt(int begin, int end){
		end++;
		if(begin>end){
			return -1;
		}
		return ((int)(Math.random()*(end-begin)))+begin;
	}
	
	public static Individual getIndividualRouletteWheel(SortedSet individuals){
		
		/*
		 * 1	1..3
		 * 2	4..5
		 * 3	6
		 * 
		 * 
		 */
		
		int size = individuals.size();		// eg 10

		long rouletteSize = 0;				// 1 + 2+ .. + 10
		
		for(int i=1; i<=size; i++){
			rouletteSize += i;
		}
		
		long random = (long)(Math.random()*rouletteSize);	// 0..roulettSize-1
		
		Iterator iterator = individuals.iterator();
		
		Individual currentIndividual = null;
		long helpRouletteSize = rouletteSize;
		
		int helpIndex = 0;
		
		while (helpRouletteSize > random){
			
		}


		
		
		
		return null;
	}

}
