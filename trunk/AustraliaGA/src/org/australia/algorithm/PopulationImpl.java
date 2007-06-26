package org.australia.algorithm;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.australia.config.Config;
import org.australia.problem.Problem;
import org.australia.util.Utils;

public class PopulationImpl implements Population{

	private Problem problem;
	private SortedSet<Individual> individuals;

	// Constructor		//////////////////////////////////////////////////////////////////////////////
	public PopulationImpl(Problem problem){
		individuals = new TreeSet<Individual>();
		this.problem = problem;
	}

	public PopulationImpl(Problem problem, int amount){
		this.problem = problem;
		initialize(amount);
	}
	

	// Private Methods 	//////////////////////////////////////////////////////////////////////////////
	private void initialize(int amount){
		individuals = new TreeSet<Individual>();

		
		//jc: we cannot use getSize due to the danger of an endless loop
		for(int i=0; i< (int)(amount * Config.getPercentageGreedy()); i++){
			individuals.add(IndividualImpl.generateGreedyIndividual(this.getProblem()));
		}
		
		System.out.println("Greedy generated: " + individuals.size());

		while(getSize() < amount){
			individuals.add(IndividualImpl.generateRandomIndividual(this.getProblem()));	
		}
		
		System.out.println("Individuals created");
	}
	
	public void selectBestHalf(){
		if(getSize()<=0){
			return;
		}
		Iterator<Individual> iterator = individuals.iterator();
		
		SortedSet<Individual> bestIndividuals = new TreeSet<Individual>();

		for(int i=0; i< getSize() /2;i++){
			bestIndividuals.add(iterator.next());
		}
		this.individuals = bestIndividuals;

	}

	public void selectBest(int size){
		if(getSize()<=0 || size > this.getSize()){
			return;
		}

		while(this.getSize() > size){
			this.remove(this.getWorstIndividual());
		}
	}
	
	public Individual getBestIndividual() {
		return individuals.first();
	}
	
	public Individual getBestValidIndividual(){
		
		Iterator<Individual> iterator = individuals.iterator();
		
		Individual currentIndividual = null;
		
		while(iterator.hasNext()){
			currentIndividual = iterator.next();
			if(currentIndividual.isValid()){
				return currentIndividual;
			}
		}
		
		// if no individual is valid return null
		return null;
	}

	public Individual getWorstIndividual() {
		return individuals.last();
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
		
		if(number >= individuals.size()){
			return null;
		}

		Iterator<Individual> iterator = individuals.iterator();
		
		Individual currentIndividual = null;
		
		for(int i=0; i<= number; i++){
			currentIndividual = iterator.next();
		}

		return currentIndividual;
	}

	public int getSize() {
		return individuals.size();
	}

	public boolean add(Individual individual) {
		return individuals.add(individual);
	}

	public boolean remove(Individual individual) {
		return individuals.remove(individual);
	}

	

	
}
