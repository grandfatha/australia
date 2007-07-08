package org.australia.algorithm;

import org.apache.log4j.Logger;
import org.australia.problem.Problem;

public class MultipleGA {
	
	Problem problem;
	
	private static Logger logger = Logger.getLogger(MultipleGA.class);
	
	public MultipleGA(Problem problem) {
		super();
		this.problem = problem;
	}



	public Individual startMultipleAlgorithm(int iterations, int populationSize, Criterion criterion, int value){
		
		// this will be our super-population
		Population population = new PopulationImpl(problem); 	// empty population

		// Get best individuals from #iterations runs
		for(int i=1; i<=iterations;i++){
			logger.info("Run iteration " + i + " from " + iterations);
			GA ga = new GA(problem);
			population.add(ga.startAlgorithm(populationSize, criterion, value));
		}
		
		// add some random individuals to super generation
		while(population.getSize() < populationSize){
			population.add(IndividualImpl.generateRandomIndividual(problem));
		}
		
		// run ga on super population
		logger.info("Run super population");
		GA ga = new GA(problem);
		return ga.startAlgorithm(population, criterion, value);

	}


}
