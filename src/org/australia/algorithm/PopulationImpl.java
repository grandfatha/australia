package org.australia.algorithm;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.australia.problem.Problem;
import org.australia.problem.ProblemHolmberg;
import org.australia.util.Utils;

public class PopulationImpl implements Population{

	private Problem problem;
	private SortedSet<Individual> individuals;
	private Individual best;
	

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
		
		while(getSize() < amount){
			individuals.add(IndividualImpl.generateRandomIndividual(this.getProblem()));
		}
		System.out.println("Individuals created");
	}
	
//	public void evolve(){
//		
//		long counter = 0;
//		
//		while(counter < 10000000){
//			
//			Individual mum = getRandomIndividual();
//			Individual dad = getRandomIndividual();
//			Parents parents = new Parents(mum, dad);
//			Individual baby = parents.haveHotAndLongAndPossiblySweatySex();
//
//// durch die strafkosten nicht mehr nötig
////			if(!mum.checkConstraints()){
////				System.out.println("Ungültige Mum");
////			}
//			
//			// mutate up to x times
//			if(Math.random() < 0.9){
//				baby.mutate();
//			}
//			
//			
//			// insert new individual
//			if(baby.getFitness() < dad.getFitness() && baby.getFitness() < mum.getFitness()){
//				if(!individuals.contains(baby)){
//					individuals.remove(getWorstIndividual());
//					individuals.add(baby);
//				}
//			}
//			
//			if ((counter % 10000)==0) {
//				System.out.println(getBestIndividual());	
//			}
//			
//
//			
//			counter++;
//		}
//	}
//	
//	public void evolveRoulette(){
//		Population newGeneration = new PopulationImpl(this.problem);
//		
//		newGeneration.add(getBestIndividual());
//		
//		while(newGeneration.getSize() < this.getSize() *2){
//			Individual mum = this.getIndividualByRouletteWheel();
//			Individual dad = this.getIndividualByRouletteWheel();
//		
//			Individual baby = new Parents(mum, dad).haveHotAndLongAndPossiblySweatySex();
//			
//			baby.mutate();
//			
//			if(Math.random() < 0.9){
//				baby.mutate();
//				if(Math.random() < 0.6){
//					baby.mutate();
//					if(Math.random() < 0.3){
//						baby.mutate();
//			}}}
//
//			
//			newGeneration.add(baby);
//		}
//		
//		newGeneration.selectBestHalf();
//
////		System.out.println(newGeneration.getSize());
//		individuals = newGeneration.getIndividuals();
//	}
	
	//TODO check
	//TODO better
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

	public Individual getBestIndividual() {
		return individuals.first();
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
