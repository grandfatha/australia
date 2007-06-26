package org.australia.algorithm;

import org.australia.problem.Problem;

public class GA {
	// Variables
	private Problem problem;
	private Population currentPopulation;
	private int populationSize;

	// Variables for stop criteria
	private Criterion criterion;
	private int totalIterations;
	private int currentIteration = 0;
	private int maxTimeNoImprovements;
	private Double currentBestFitness;
	private long currentBestFitnessTime;
	
	
	/* Constructor	*****************************************************************************************************************/
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
		this.criterion = Criterion.ITERATIONS;
		this.totalIterations = iterations;
		this.populationSize = populationSize;
		return startAlgorithm();
	}
	
	public Individual startAlgorithm(int populationSize, Criterion criterion, int value){
		this.populationSize = populationSize;
		this.criterion = criterion;

		if(criterion.equals(Criterion.ITERATIONS)){
			totalIterations = value;
		}else if(criterion.equals(Criterion.TIMENOIMPROVEMENTS)){
			maxTimeNoImprovements = value;
		}
		
		return startAlgorithm();
	}

	
	
	private Individual startAlgorithm(){


		/* create start population **************************************************/
		currentPopulation = new PopulationImpl(problem, populationSize);

		/* evolve ********************************************************************/
		
		while(!stop()){
			Population newGeneration = new PopulationImpl(this.problem);

			/* adds the best individual to new generation ****************************/
			newGeneration.add(currentPopulation.getBestIndividual());
			
			/* add 10% random indiduals from foreign countries to new population *****/
//			for(int j=0; j < populationSize/5; j++){
//				newPopulation.add(IndividualImpl.generateRandomIndividual(problem));
//			}
			
			/* create a new generation with doubled size as current ******************/
			while(newGeneration.getSize() < populationSize * 2){		// size of new population
																		// higher value results in higher selection pressure

				/* selection *******************************************************/
//				Individual mum = currentPopulation.getIndividualByRouletteWheel();
//				Individual dad = currentPopulation.getIndividualByRouletteWheel();

				Individual mum = currentPopulation.getRandomIndividual();
				Individual dad = currentPopulation.getRandomIndividual();


				/* recombine *******************************************************/
				Individual baby = mum.haveSex(dad);
				

				/* mutate **********************************************************/
				if(Math.random() < 0.7){
					for(int j=0; j< (int)(Math.random()*5);j++){ 	// mutate up to 10 times
						baby.mutate();
					}
				}
				
				if(Math.random() < 0.3){
					baby.mutateBanFacilityAndFindNewFacilityByRouletteWheel();
				}
				
				if(Math.random() < 0.1){
					baby.mutateBanFacility();	// delete a random facility to save fixcosts
				}
				
				/* add new individual to new generation ****************************/
				newGeneration.add(baby);
			}
			
			/* finally select best of new Generation *******************************/
			newGeneration.selectBest(populationSize);
			
			/* replace current population by new Population ************************/
			currentPopulation = newGeneration;
			
			/* Print best indivual every 100 times *********************************/
			if(currentIteration % 100 == 0){
				System.out.println(currentPopulation.getBestIndividual());
			}
			
		} // End for
		
		/* return best Individual *************************************************/
		return currentPopulation.getBestIndividual();
	}
	
	private boolean stop(){
		currentIteration++;
		
		
		if(this.criterion.equals(Criterion.ITERATIONS)){		// stop after x generations
			if(currentIteration > totalIterations){
				return true;
			}
		}else if(this.criterion.equals(Criterion.TIMENOIMPROVEMENTS)){			// Stop ga after x seconds with no improvement
			if(currentBestFitness == null || currentPopulation.getBestIndividual().getFitness() < currentBestFitness){
//				System.out.println("Found a new Best");
				currentBestFitness = currentPopulation.getBestIndividual().getFitness();
				currentBestFitnessTime = System.currentTimeMillis();
			}else{
				if(System.currentTimeMillis() - currentBestFitnessTime > maxTimeNoImprovements*1000){
					return true;
				}
			}
		}
		
		return false;
	}

}
