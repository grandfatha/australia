package org.australia.algorithm;

import org.australia.problem.Problem;

public class GA {
	Problem problem;
	Population currentPopulation;
	
	
	

	public GA(Problem problem) {
		super();
		this.problem = problem;
	}

	public Individual startAlgorithm(int populationSize, int iterations){

		// create start population
		currentPopulation = new PopulationImpl(problem, populationSize);

		// evolve
		for(int i=0; i<iterations; i++){
			Population newGeneration = new PopulationImpl(this.problem);
			
			// adds the best individual to new generation
			newGeneration.add(currentPopulation.getBestIndividual());
			
			while(newGeneration.getSize() < currentPopulation.getSize() *2){

				Individual mum = currentPopulation.getIndividualByRouletteWheel();
				Individual dad = currentPopulation.getIndividualByRouletteWheel();
			
				Individual baby = mum.haveSex(dad);
				
				baby.mutate();
				baby.mutate();
				baby.mutate();
				
				newGeneration.add(baby);
			}
			
			newGeneration.selectBestHalf();

			currentPopulation = newGeneration;
			
			if(i%10==0)
				System.out.println(currentPopulation.getBestIndividual());
			
		} // Ende for
		
		
		return currentPopulation.getBestIndividual();
	}

}
