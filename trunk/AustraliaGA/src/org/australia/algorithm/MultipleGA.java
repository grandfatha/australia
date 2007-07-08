package org.australia.algorithm;

import org.australia.problem.Problem;

public class MultipleGA {
	
	Problem problem;
	
	
	
	public MultipleGA(Problem problem) {
		super();
		this.problem = problem;
	}



	public Individual startMultipleAlgorithm(int iterations, int populationSize, Criterion criterion, int value){
		
		// this will be our super-population
		Population population = new PopulationImpl(problem); 	// empty population

		// Get best individuals from #iterations runs
		for(int i=0; i<iterations;i++){
			GA ga = new GA(problem);
			population.add(ga.startAlgorithm(populationSize, criterion, value));
		}
		
		// run ga on super population
		GA ga = new GA(problem);
		return ga.startAlgorithm(population, criterion, value*10);

	}


}
