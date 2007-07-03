package org.australia.algorithm;

import java.util.Calendar;

import org.australia.config.Config;
import org.australia.problem.Problem;

public class GA {
	// Variables
	private Problem problem;
	private Population currentPopulation;
	private int populationSize;
	
	// Status
	private Status status;

	// Variables for stop criteria
	private Criterion criterion;
	private int totalIterations;
	private int currentIteration = 0;
	private int maxTimeNoImprovements;
	private Double currentBestFitness;
	private Long startTime;
	private Long stopTime;
	private Long currentBestFitnessTime;
	
	
	/* Constructor	*****************************************************************************************************************/
	public GA(Problem problem) {
		super();
		status = new Status();
		this.problem = problem;
	}

	
	/* start algorithm methods	*****************************************************************************************************/


	/**
	 * create a new startpopulation and run the Genetic Algorithm.
	 * 
	 * @deprecated use 	public Individual startAlgorithm(int populationSize, Criterion criterion, int value) instead
	 * 
	 * @param populationSize
	 * @param iterations
	 * @return best Individual
	 */
	@Deprecated
	public Individual startAlgorithm(int populationSize, int iterations){
		this.criterion = Criterion.ITERATIONS;
		this.totalIterations = iterations;
		this.populationSize = populationSize;
		return startAlgorithm();
	}
	
	/**
	 * create a new startpopulation and run GA.
	 * 
	 * @param populationSize
	 * @param criterion
	 * @param value
	 * @return Individual
	 * @author jochen
	 */
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
	

	/**
	 * 
	 * start algotihm with a given startpopulation
	 * 
	 * @param startPopulation
	 * @param criterion
	 * @param value
	 * @return best Individual that was found by the GA.
	 * @author jochen
	 */
	public Individual startAlgorithm(Population startPopulation, Criterion criterion, int value){
		currentPopulation = startPopulation;
		return startAlgorithm(startPopulation.getSize(), criterion, value);
	}

	
	
	
	/**
	 * this implements the core of the algorithm
	 * use a starter method to define the abort criteria, eg # iterations or duration
	 * 
	 * @return best Individual that was found (valid and invalid)
	 * @author jochen
	 */
	private Individual startAlgorithm(){
		
		startTime = System.currentTimeMillis();
//		System.out.println("Start Algorithm for Problem " + problem.getInstanceName() + " (Fees: " + Config.getFee() +")");


		/* create start population **************************************************/
		if(currentPopulation==null){
			currentPopulation = new PopulationImpl(problem, populationSize);
		}
		
//		System.out.println(currentPopulation.getBestIndividual());
		

		/* evolve ********************************************************************/
		
		while(!stop()){
			
			/* create a new empty child-population ***********************************/
			Population newGeneration = new PopulationImpl(this.problem);

			/* adds the best individual to new generation ****************************/
			newGeneration.add(currentPopulation.getBestIndividual());
			
			/* add some random indiduals from foreign countries to new population *****/
			for(int j=0; j < populationSize * Config.getPercentageForeignIndividuals(); j++){
				currentPopulation.remove(currentPopulation.getWorstIndividual());
			}
			for(int j=0; j < populationSize * Config.getPercentageForeignIndividuals(); j++){
				currentPopulation.add(IndividualImpl.generateRandomIndividual(problem));
			}
			
			/* create a new generation with doubled size as current ******************/
			
			while(newGeneration.getSize() < populationSize * Config.getNewGenerationSize()){		// size of new population
																									// higher value results in higher selection pressure

				/* selection *******************************************************/
				
				Individual mum=null, dad=null;
				
				if(Config.getSelectionMethod() == 0){		// random
					mum = currentPopulation.getRandomIndividual();
					dad = currentPopulation.getRandomIndividual();
				}else if(Config.getSelectionMethod()==1){	// roulette wheel
					mum = currentPopulation.getIndividualByRouletteWheel();
					dad = currentPopulation.getIndividualByRouletteWheel();
				}


				/* recombine *******************************************************/
				
				Individual baby = mum.haveSex(dad);
				

				/* mutate **********************************************************/
				
				if(Math.random() < Config.getOddsMutation()){
	
					double random = Math.random();
					
					if(random < 0.6){
						baby.mutateNearNeighbor();
					}else if (random < 0.7){
						baby.mutateNearNeighbor();
						baby.mutateNearNeighbor();
						baby.mutateNearNeighbor();
//					}else if(random < 0.8){
//						baby.mutateSwitchFacilities();
					}else{
						baby.mutateBanFacilityAndFindNewFacilityByRouletteWheel();
					}
				}
				

				/* add new individual to new generation ****************************/
				newGeneration.add(baby);
				
			}
			
			/* finally select best of new Generation *******************************/
			newGeneration.selectBest(populationSize);
			
			/* replace current population with the new population ************************/
			currentPopulation = newGeneration;
			
			/* Print best indivual every n times *********************************/
			if(Config.getPrintEachTimes()!=0 && currentIteration % Config.getPrintEachTimes() == 0){
				System.out.println(currentPopulation.getBestIndividual());
			}
			
		} // End for

		status.setTimeStopped(Calendar.getInstance());
		stopTime = System.currentTimeMillis();

		/* return best Individual *************************************************/
		return currentPopulation.getBestIndividual();

	}
	
	
	
	// handles the stop criterion
	private boolean stop(){
		status.setCurrentIteration(currentIteration);
		
		// some time info
		if(currentBestFitness == null || currentPopulation.getBestIndividual().getFitness() < currentBestFitness){

			status.setCurrentBestIndividual(currentPopulation.getBestIndividual());
			status.setCurrentBestValidIndividual(currentPopulation.getBestValidIndividual());
			
			currentBestFitness = currentPopulation.getBestIndividual().getFitness();
			currentBestFitnessTime = System.currentTimeMillis();
		}
		
		
		if(this.criterion.equals(Criterion.ITERATIONS)){		// stop after x generations
			if(currentIteration > totalIterations){
				return true;
			}
		}else if(this.criterion.equals(Criterion.TIMENOIMPROVEMENTS)){			// Stop ga after x seconds with no improvement
			if(currentBestFitness == null || currentPopulation.getBestIndividual().getFitness() < currentBestFitness){
			}else{
				if(System.currentTimeMillis() - currentBestFitnessTime > maxTimeNoImprovements*1000){
					return true;
				}
			}
		}

		currentIteration++;
		
		return false;
	}
	 
	@Deprecated
	public double getDuration(){
		if(stopTime==null){
			System.out.println("Error: Algorithm not finished yet");
			return -1.0;
		}
		
		return (stopTime - startTime) / 1000 ;
	}
	
	public double getDurationUntilBestWasFound(){
		if(currentBestFitnessTime==null){
			throw new RuntimeException("No time");
		}
		
		return (currentBestFitnessTime - startTime) / 1000 ;
	}
	
	public Status getStatus() {
		return status;
	}

}
