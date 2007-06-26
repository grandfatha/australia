package org.australia.util;

import java.util.Iterator;
import java.util.SortedSet;

import org.australia.algorithm.Individual;
import org.australia.algorithm.IndividualImpl;

public class Utils {
	
	
	public static boolean[] getRandomPattern(int length){
		
		boolean[] pattern = new boolean[length];
		
		for (int i = 0; i < length; i++) {
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
	
	
	
	
	public static Individual makeSelection(SortedSet<IndividualImpl> individuals) {

		int fitnessSum = 0;
		int[] fitnessCumulative = new int [individuals.size()];

		Iterator<IndividualImpl> iterator = individuals.iterator();
		
//		Individual currentIndividual = null;
		
		for (int i=0; i<individuals.size(); i++) {
			/* give at least a fitness of one for the roulettewheel.
			 * to make sure an individual without score can still
			 * chosen, and in case of no scores, to make sure that not
			 * only the first individual is taken
			 */
			fitnessSum += iterator.next().getFitness();
			fitnessCumulative[i] = fitnessSum;
		}

			int turn = (int) (Math.random() * fitnessSum);

			int j = 0;

			
			
			while (fitnessCumulative[j] < turn) {
			  j++;
			}
			
			System.out.println("Nr. " + j + " von " + individuals.size());
			
			Iterator<IndividualImpl> newIterator = individuals.iterator();
			Individual selectedIndividual = null;
			
			for (int i = 0; i < j; i++) {
				selectedIndividual = newIterator.next();
			}
			
		return selectedIndividual;
	}
	
	
	
/*	public static Individual getIndividualRouletteWheel(SortedSet<IndividualImpl> individuals){
		
		
		 * 1	1..3
		 * 2	4..5
		 * 3	6
		 * 
		 * 
		 
		
		int size = individuals.size();		// eg 10

		long rouletteSize = 0;				// 1 + 2+ .. + 10
		
		for(int i=1; i<=size; i++){
			rouletteSize += i;
		}
		
		long random = (long)(Math.random()*rouletteSize);	// 0..roulettSize-1
		random++;		// 1 .. rouletteSize
		
		Iterator<IndividualImpl> iterator = individuals.iterator();
		
		Individual currentIndividual = null;
		long helpRoulette = 0;
//		int helpSize = size;
		
//		int helpIndex = 0;
		
		for(int helpIndex=1;helpRoulette <= random; ){
			currentIndividual = iterator.next();
			helpRoulette += helpIndex;
			helpIndex++;
		}
		
		
		 * beispiel oben
		 * individuals = 123
		 * size = 3
		 * rouletteSize = 1+2+3 = 6
		 * 
		 * random = 		2	
		 * 						
		 * helpIndex = 		1	2
		 * helpRoulette = 	0	1
		 * 					
		 * cI = 			1	
		 * 
		 * 
		 * 
		 * 
		 



		
		
		
		return currentIndividual;
	}
	
	public static void main(String[] args) {
			
		
		
		 * 1	1..3
		 * 2	4..5
		 * 3	6
		 * 
		 * 
		 
		
		for (int la = 0; la < 10; la++) {
			
		
		int size = 3;		// eg 10

		long rouletteSize = 0;				// 1 + 2+ .. + 10
		
		for(int i=1; i<=size; i++){
			rouletteSize += i;
		}
		
		long random = (long)(Math.random()*rouletteSize);	// 0..roulettSize-1
		random++;		// 1 .. rouletteSize

//		System.out.println("Size: " + size);
//		System.out.println("RouletteSize: " + rouletteSize);
		System.out.println("Random: " + random);
		
		int ind = 0;

		long helpRoulette = rouletteSize;
		
		for(int helpIndex=size;helpRoulette >= random; ){
			ind++;
			helpRoulette -= helpIndex;
			helpIndex--;
		}

		System.out.println("Zugeordnet: " + ind);
		System.out.println();

		}

		
	}*/

	
	/**
	 * returns value with highest chance for lowest value
	 * @param size range in which values are set 
	 * @return random value 0..size-1
	 * @author jochen
	 */
	public static int rouletteWheel(int size){

		long cumValue=0;
		long[] cumValues = new long[size];
		
		for(int s=size, i=0; i<size; i++, s--){
			cumValue += s;
			cumValues[i] = cumValue;
		}
		
		long random = ((long)(Math.random()*cumValue))+1;	// 1..size

		int currentValue=0;
		
		for(currentValue=0; currentValue < size; currentValue++){
			if(cumValues[currentValue] >= random){
				break;
			}
		}

		/*
		 * size = 3
		 * cumValues[0] = 3
		 * cumValues[1] = 5
		 * cumValues[2] = 6
		 * 
		 * (random 0 => 0)
		 * random 1 => 0
		 * random 2 => 0
		 * random 3 => 0
		 * random 4 => 1
		 * random 5 => 1
		 * random 6 => 2
		 */
		
		return currentValue;
	}
	

	public static void main(String[] args) {
		for(int i=0; i<10;i++){
			rouletteWheel(3);
		}
	}



}
