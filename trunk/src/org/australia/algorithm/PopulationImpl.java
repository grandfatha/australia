package org.australia.algorithm;

import java.util.Collection;
import java.util.HashSet;
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
	
	public Individual getBest() {
		return best;
	}

	// Constructor		//////////////////////////////////////////////////////////////////////////////
	public PopulationImpl(Problem problem, int amount){
		this.problem = problem;
		initialize(amount);
	}
	
	public PopulationImpl(Problem problem){
		individuals = new TreeSet<Individual>();
		this.problem = problem;
	}

	public void initialize(int amount){
		individuals = new TreeSet<Individual>();
		
		for (int i = 0; i < amount; i++) {
			individuals.add(IndividualImpl.generateRandomIndividual(this.getProblem()));
			System.out.println("Created Individual Number: " + i);
		}
	}
	
	public void evolve(){
		
		long counter = 0;
		
		while(counter < 10000000){
			
			Individual mum = getRandomIndividual();
			Individual dad = getRandomIndividual();
			Parents parents = new Parents(mum, dad);
			Individual baby = parents.haveHotAndLongAndPossiblySweatySex();

// durch die strafkosten nicht mehr nötig
//			if(!mum.checkConstraints()){
//				System.out.println("Ungültige Mum");
//			}
			
			// mutate up to x times
			if(Math.random() < 0.9){
				baby.mutate();
			}
			
			
			// insert new individual
			if(baby.getFitness() < dad.getFitness() && baby.getFitness() < mum.getFitness()){
				if(!individuals.contains(baby)){
					individuals.remove(getWorstIndividual());
					individuals.add(baby);
				}
			}
			
			if ((counter % 10000)==0) {
				System.out.println(getBestIndividual());	
			}
			

			
			counter++;
		}
	}
	
	public void evolveRoulette(){
		Population newGeneration = new PopulationImpl(this.problem);
		
		newGeneration.add(getBestIndividual());
		
		while(newGeneration.getSize() < this.getSize() *2){
			Individual mum = this.getIndividualByRouletteWheel();
			Individual dad = this.getIndividualByRouletteWheel();
		
			Individual baby = new Parents(mum, dad).haveHotAndLongAndPossiblySweatySex();
			
			baby.mutate();
			
			if(Math.random() < 0.9){
				baby.mutate();
				if(Math.random() < 0.6){
					baby.mutate();
					if(Math.random() < 0.3){
						baby.mutate();
			}}}

			
			newGeneration.add(baby);
		}
		
		newGeneration.selectBestHalf();

//		System.out.println(newGeneration.getSize());
		individuals = newGeneration.getIndividuals();
	}
	
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
		Iterator<Individual> iterator = individuals.iterator();
		
		int random = (int)(Math.random()*individuals.size());
		
		Individual currentIndividual = null;
		
		for (int i = 0; i <= random; i++) {
			currentIndividual = iterator.next();
		}
		return currentIndividual;
		
	}
	
	public Individual getIndividualByRouletteWheel(){

		int rouletteRandom = Utils.rouletteWheel(individuals.size());	// 0..size-1
		
		return getIndividual(rouletteRandom);
		
	}
	


	public SortedSet<Individual> getIndividuals() {
		return individuals;
	}

	public Problem getProblem() {
		return problem;
	}

	public Individual getIndividual(int number) {
		Iterator<Individual> iterator = individuals.iterator();
		
		Individual currentIndividual = null;
		
		for(int i=0; i<= number && iterator.hasNext(); i++){
			currentIndividual = iterator.next();
		}

		return currentIndividual;
	}

	
	
	
	public static void main(String[] args) {
		
		Problem problem = ProblemHolmberg.readProblem("problem/p67");
		

		PopulationImpl a = new PopulationImpl(problem, 100);
		
//		
//		double best = 99999999999.0;
////		for (int i = 0; i < 1000000; i++) {
//		while(best > 15000){
//			
//			Individual individual = Individual.generateRandomIndividual(a.getProblem());
////			System.out.println(individual.toString());
////			System.out.println(individual.checkConstraints());
////			System.out.println(individual.calculateFitness());
//			
//			if(individual.calculateFitness() < best){
//				best = individual.calculateFitness();
//				System.out.println(best + " " + individual.toString());
//			}
//			
//		
//		}
//		System.out.println(best);
//		
		

		
//		System.out.println("Begin init");
//		a.initialize(500);
		System.out.println("Begin evolve");
		
		a.evolve();

		System.out.println(a.getBestIndividual().getFitness() + " " + a.getBestIndividual());
		
		System.out.println("Ende");
		
		
		
		
//		Problem problem = a.getProblem();
//		a.initialize(100);
//		Individual randomIndividual = a.getRandomIndividual();
//		
//		double customers = problem.getCustomers();		// 50
//		double warehouses = problem.getWarehouses();	// 10
//		double[][] costs = problem.getCosts();
//		
//		double fitness1 = 0, fitness2=0;
//		
//		for (int j = 0; j < customers; j++) {
//			for (int i = 0; i < warehouses; i++) {
//				if(randomIndividual.getGene()[j]-1 == i){
//					fitness1 += costs[j][i];
//				}
////				System.out.println();
//				System.out.print(costs[j][i] + ",\t");
//			}
//			System.out.println();
//			
//			System.out.println(randomIndividual.getGene()[j]-1 +": " + costs[j][randomIndividual.getGene()[j]-1]);
//			fitness2+=costs[j][randomIndividual.getGene()[j]-1];
//		}
//		System.out.println("f1: " + fitness1);
//		System.out.println("f2: " + fitness2);
//		

//			for (int i = 0; i < a.getBestIndividual().getGene().length; i++) {
//				System.out.println(a.getBestIndividual().getGene()[i]);
//			}
		
		System.out.println("Fees: " + a.getBestIndividual().getFeeCosts());
		
		System.out.println("Current Population:");
		for (Individual ind : a.getIndividuals()) {
			System.out.println(ind.getFitness() + " " + ind.toString());
		}
		System.out.println();
		
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
