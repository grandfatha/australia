package org.australia.core;

import java.util.Collection;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.australia.commons.Individual;
import org.australia.commons.Parents;
import org.australia.problem.Problem;
import org.australia.problem.ProblemHolmberg;

public class PopulationImpl {
	private Problem problem;
	private SortedSet<Individual> individuals;
	
	public PopulationImpl(){
		try{
//			this.problem = ProblemBoccia.readProblem("i50100_1.plc");
			this.problem = ProblemHolmberg.readProblem("p67");
		}catch (Exception e) {
			System.out.println("Fehler beim Einlesen: " + e.getStackTrace());
			System.exit(-1);
		}
	}
	
	//init
	public void initialize(int amount){
		individuals = new TreeSet<Individual>();
		
		for (int i = 0; i < amount; i++) {
			individuals.add(Individual.generateRandomIndividual(this.getProblem()));
			System.out.println("Created Individual Number: " + i);
		}
		
	}
	
	//evolve
	
	public void evolve(){
		
		long counter = 0;
		
		while(counter < 10000000){
			
			Individual mum = getRandomIndividual();
			Individual dad = getRandomIndividual();
			Parents parents = new Parents(mum, dad);
			Individual baby = parents.haveHotAndLongAndPossiblySweatySex();

//			if(!mum.checkConstraints()){
//				System.out.println("Ungültige Mum");
//			}
			
			// mutate up to x times
			if(Math.random() < 0.9){
				baby.mutate();
				if(Math.random() < 0.5){
					baby.mutate();
					if(Math.random() < 0.3){
						baby.mutate();
						if(Math.random() < 0.3){
							baby.mutate();
						if(Math.random() < 0.3){
							baby.mutate();
						if(Math.random() < 0.3){
							baby.mutate();
						if(Math.random() < 0.3){
							baby.mutate();
							if(Math.random() < 0.3){
								baby.mutate();
								if(Math.random() < 0.3){
									baby.mutate();
									if(Math.random() < 0.3){
										baby.mutate();
										if(Math.random() < 0.3){
											baby.mutate();
											if(Math.random() < 0.3){
												baby.mutate();
												if(Math.random() < 0.3){
													baby.mutate();
						}}}}}
							}}}}}
			}}}
			
			
			// insert new individual
			if(baby.getFitness() < dad.getFitness() && baby.getFitness() < mum.getFitness()){
				if(!individuals.contains(baby)){
					individuals.remove(getWorstIndividual());
					individuals.add(baby);
				}
			}
			
			if ((counter % 10000)==0) {
				System.out.println(getBestIndividual().getFitness() + " " + getBestIndividual());	
			}
			

			
			counter++;
		}
		
		
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


	public Collection<Individual> getIndividuals() {
		return individuals;
	}

	public Problem getProblem() {
		return problem;
	}

	
	public static void main(String[] args) {

		System.out.println("Create new Population");

		PopulationImpl a = new PopulationImpl();
		
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
		

		
		System.out.println("Begin init");
		a.initialize(500);
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

	
}
