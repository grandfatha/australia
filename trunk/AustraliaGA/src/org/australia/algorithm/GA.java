package org.australia.algorithm;

import org.australia.problem.Problem;

public class GA {
	Problem problem;
	Population currentPopulation;
	
	
	

	public GA(Problem problem) {
		super();
		this.problem = problem;
	}

	/**
	 * Starts the Genetic Algorithm
	 * 
	 * @param populationSize
	 * @param iterations
	 * @return best Individual
	 */
	public Individual startAlgorithm(int populationSize, int iterations){

		/* create start population **************************************************/
		currentPopulation = new PopulationImpl(problem, populationSize);

		/* evolve ********************************************************************/
		for(int i=0; i<iterations; i++){
			Population newGeneration = new PopulationImpl(this.problem);

			/* adds the best individual to new generation ****************************/
			newGeneration.add(currentPopulation.getBestIndividual());
			
			/* create a new generation with doubled size as current ******************/
			while(newGeneration.getSize() < currentPopulation.getSize() * 1.5){		// size of new population
																					// higher value results in higher selection pressure

				/* selection *******************************************************/
				Individual mum = currentPopulation.getIndividualByRouletteWheel();
				Individual dad = currentPopulation.getIndividualByRouletteWheel();

//				Individual mum = currentPopulation.getRandomIndividual();
//				Individual dad = currentPopulation.getRandomIndividual();

				/* recombine *******************************************************/
				Individual baby = mum.haveSex(dad);
				
				/* mutate **********************************************************/
				if(Math.random() < 0.7){
					for(int j=0; j< (int)(Math.random()*10);j++){ 	// mutate up to 10 times
						baby.mutate();
					}
				}
				
				if(Math.random() < 0.3){
					baby.mutateDisallowFacility();	// delete a random facility to save fixcosts
				}
				
				/* add new individual to new generation ****************************/
				newGeneration.add(baby);
			}
			
			/* finally select best of new Generation *******************************/
			newGeneration.selectBest(currentPopulation.getSize());
			
			/* replace current population by new Population ************************/
			currentPopulation = newGeneration;
			
			/* Print best indivual every 100 times *********************************/
			if(i%100==0){
				System.out.println(currentPopulation.getBestIndividual());
			}
			
		} // End for
		
		/* return best Individual *************************************************/
		return currentPopulation.getBestIndividual();
	}

}
