package org.australia.algorithm;

import javolution.util.FastTable;

import org.australia.config.Config;
import org.australia.problem.Problem;
import org.australia.util.Utils;

public class PopulationFastTableImpl implements Population{

	private Problem problem;
	private FastTable<Individual> individuals;
	
	private boolean changed = true;

	// Constructor		//////////////////////////////////////////////////////////////////////////////
	public PopulationFastTableImpl(Problem problem){
		individuals = new FastTable<Individual>();
		individuals.setValueComparator(new IndividualComparator());
		this.problem = problem;
	}

	public PopulationFastTableImpl(Problem problem, int amount){
		this.problem = problem;
		initialize(amount);
	}
	

	// Private Methods 	//////////////////////////////////////////////////////////////////////////////
	private void initialize(int amount){
		individuals = new FastTable<Individual>();
		individuals.setValueComparator(new IndividualComparator());

		individuals.add(IndividualImpl.generateGreedyIndividual(this.getProblem()));
		
		//jc: we cannot use getSize due to the danger of an endless loop
		for(int i=0; i< (int)(amount * Config.getPercentageGreedy()); i++){
			individuals.add(IndividualImpl.generateGreedyIndividualWithRouletteWheel(this.getProblem()));
		}
		
		System.out.println("Greedy generated: " + individuals.size());

		while(getSize() < amount){
			individuals.add(IndividualImpl.generateRandomIndividual(this.getProblem()));
		}
		System.out.println("Individuals created");
	}

	public void selectBest(int size){
		if(getSize()<=0 || size > this.getSize()){
			return;
		}
		sort();
		individuals.removeRange(size, individuals.size());

	}
	
	public Individual getBestIndividual() {
		sort();
		return individuals.getFirst();
	}
	
	public Individual getBestValidIndividual(){
		sort();
		Individual currentIndividual = null;
		
		for (int i = 0, n = individuals.size(); i < n; i++) {
			currentIndividual = individuals.get(i);
			if(currentIndividual.isValid()){
				return currentIndividual;
			}
	     }
		
		// if no individual is valid return null
		return null;
	}

	public Individual getWorstIndividual() {
		sort();
		return individuals.getLast();
	}

	public Individual getRandomIndividual(){
		int random = (int)(Math.random()* this.getSize());	// 0..size-1
		
		return getIndividual(random);
		
	}
	
	public Individual getIndividualByRouletteWheel(){

		int rouletteRandom = Utils.rouletteWheel(individuals.size());	// 0..size-1
		
		return getIndividual(rouletteRandom);
		
	}

	public Problem getProblem() {
		return problem;
	}

	/* 
	 * returns the individual at given number
	 * number between 0..n-1
	 * returns null if invalid number
	 * @see org.australia.algorithm.Population#getIndividual(int)
	 */
	public Individual getIndividual(int number) {
		if(number >= getSize()){
			return null;
		}

		sort();

		return individuals.get(number);
	}

	public int getSize() {
		return individuals.size();
	}

	public boolean add(Individual individual) {
		changed = true;
		individuals.addLast(individual);
		return true;
		
	}

	public boolean remove(Individual individual) {
		return individuals.remove(individual);
	}

	private void sort(){
		if(changed==true){
			individuals.sort();
		}
		changed=false;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		sort();
		for(int i=0; i<individuals.size(); i++){
			result.append(getIndividual(i).toString());
			result.append("\n");
		}

		return result.toString();
	}

	
}
