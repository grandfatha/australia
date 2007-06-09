package org.australia.commons;

import java.util.Collection;
import java.util.HashSet;
import org.australia.problem.*;

public class Individual implements Comparable<Individual> {

	private int[] gene;		// size: Number of customers; int[0] = 123 means customer 0 gets goods from warehouse 123
	private Double fitness;
	
	private Problem problem;
	
	// Constructor		//////////////////////////////////////////////////////////////////////////
	private Individual(Problem problem) {
		this.problem = problem;
	}
	
	
	private Individual(Problem problem,  int[] gene) {
		this.problem = problem;
		this.gene = gene;
		this.calculateFitness();
	}

	// Factory methods		//////////////////////////////////////////////////////////////////////////
	/**
	 * Generate a new Individual
	 * @param problem
	 * @param gene
	 * @return new Individual
	 * null if invalid
	 */
	public static Individual createInstance(Problem problem, int[] gene){
		if(problem==null || gene ==null){
			throw new RuntimeException("Problem oder gene is null");
			//			return null;
		}
		
		Individual individual = new Individual(problem, gene);
		
//		if(!individual.checkConstraints()){
//			return null;
//		}
		return individual;
		
	}
	
	/**
	 * Generate a completely random Individual
	 * @param problem
	 * @return Individual
	 * @author jochen
	 */
	public static Individual generateRandomIndividual(Problem problem){
		Individual result = new Individual(problem);
		
		// number of customers
		int numberOfCustomers = (int)problem.getCustomers();
		
		// generate an empty gene
		result.gene = new int[numberOfCustomers];

		// number of warehouses
		double warehouses = problem.getWarehouses();
		
		// counter for trys with invalid genes
		int counter = 0;

		// Loop until we get a valid Individual
//		do{
			
			// assign every customer a random warehouse
			//TODO make less random and improve nearest neighbor...
			for (int i = 0; i < numberOfCustomers; i++) {
				result.gene[i] = getRandomWarehouse(warehouses);
			}
			
			// if no valid gene can be generated in 1000 trys throw exception
			if(++counter > 100000000){
				System.out.println("Could not generate valid gene");
				throw new RuntimeException("Could not generate valid gene");
			}
			
//		}while(!result.checkConstraints());
		
		// calculate fitness and save to instance variable
		result.calculateFitness();
		
		return result;
	}

	/**
	 * Warehouses are in the range from 0 to #warehouses-1
	 * @param warehouses
	 * @return a random warehousenumber
	 */
	private static int getRandomWarehouse(double warehouses){
		
		//TODO testen

		return ((int) (Math.random()*warehouses));	// 0 .. ;warehouses-1
		
	}

	
	// Instance methodes 		////////////////////////////////////////////////////////////////////////////////

	
	/**
	 * Check constraints
	 * 
	 * Current constraints are checked
	 * - every customer gets goods from (only) one warehouse (implicit)
	 * - warehouse max. capacity
	 * @return true if valid
	 * @author jochen
	 */
	public boolean checkConstraints(){
		// prevent gene is null
		if(gene==null){
			throw new RuntimeException("Gene is null");
		}
		
		// copy values
		double[] cap = problem.getCap().clone();	// double[]
		double[] need = problem.getNeeds();	// double[]
		
		assert(gene.length == problem.getCustomers());
	

		// i	number of customer
		for (int i = 0; i < gene.length; i++) {
			// i: number of customer
			//			need[i]	// Need of customer i (0..n-1)
			//			gene[i] // Number of Warehouse for customer i (0..n-1)
			//			cap[j]  // capacity of warehouse j (0..n-1)

			// needs will be deduced from capacity of current warehouse
			cap[gene[i]] = cap[gene[i]] - need[i];

			// check if remaining cap >= 0
			if(cap[gene[i]] < 0){
				return false;
			}
		}

		return true;
	}
	
	
	public void calculateFitness(){
		if (gene==null){
			throw new RuntimeException("Gene is null");
		}

		double f = 0.0;
		
		
		// add transport costs
		double customers = problem.getCustomers();		// 50
		double[][] costs = problem.getCosts();

		assert(costs.length== gene.length);

//		double warehouses = problem.getWarehouses();	// 10
//		for (int i = 0; i < warehouses; i++) {
//			for (int j = 0; j < customers; j++) {
//				if(gene[j]-1 == i){
//					fitness += costs[j][i];
//				}
//			}
//		}
//		
		// get the costs from customer i to its warehouse (gene[i])
		for (int i = 0; i < customers; i++) {
			f += costs[i][gene[i]];
		}
		
		
//		double temp1 = fitness;
		
		// add fixcosts
		double[] fixcosts = problem.getFixcosts();
		
		Collection<Integer> warehouseSet = new HashSet<Integer>();
		for (int g : gene) {
			warehouseSet.add(g);
		}
		
		for (Integer currentWareHouse : warehouseSet) {
			f += fixcosts[currentWareHouse];
		}
		
		// strafkosten / M-Method
		double fee = 50; 	// fee for one units thats more in warehouse than possible
		
		double[] assigned = new double[(int) getProblem().getWarehouses()];
		double[] need = getProblem().getNeeds();
		
		for (int i = 0; i < gene.length; i++) {
			// i: number of customer
			//			need[i]	// Need of customer i (0..n-1)
			//			gene[i] // Number of Warehouse for customer i (0..n-1)
			//			cap[j]  // capacity of warehouse j (0..n-1)

			assigned[gene[i]] += need[i];
		}
		
		// each unit in assgigned > cap
		for(int i=0; i<assigned.length;i++){
			if(assigned[i] > getProblem().getCap()[i]){
				f += (assigned[i] - getProblem().getCap()[i]) * fee;
			}
		}
		
//		System.out.println("Kosten: " + (fitness - temp1));
		
		this.fitness = f;
	}
	
	public double getFeeCosts(){
		double feecosts =0.0;
		
		// strafkosten / M-Method
		double fee = 10; 	// fee for one units thats more in warehouse than possible
		
		double[] assigned = new double[(int) getProblem().getWarehouses()];
		double[] need = getProblem().getNeeds();
		
		for (int i = 0; i < gene.length; i++) {
			// i: number of customer
			//			need[i]	// Need of customer i (0..n-1)
			//			gene[i] // Number of Warehouse for customer i (0..n-1)
			//			cap[j]  // capacity of warehouse j (0..n-1)

			assigned[gene[i]] += need[i];
		}
		
		// each unit in assgigned > cap
		for(int i=0; i<assigned.length;i++){
			if(assigned[i] > getProblem().getCap()[i]){
				feecosts += (assigned[i] - getProblem().getCap()[i]) * fee;
			}
		}
		
		return feecosts;
		
	}
	
	//TODO make less random
	//TODO daniel wants a true mutation
	public void mutate(){
		
		int randomCustomer = (int)(Math.random() * gene.length);	// 0 .. n-1
		
		int randomWarehouse = (int)(Math.random() * getProblem().getWarehouses());	// 0 .. n-1
		
		gene[randomCustomer] = randomWarehouse;
		calculateFitness();
		
	}
	
	public static void p(Object o){
		System.out.println(o);
	}
	
	public void calculateFitnessTest(){
		if (gene==null){
			throw new RuntimeException("Gene is null");
		}

		double fitness = 0.0;
		
		
		// add transport costs
		double customers = problem.getCustomers();		// 50
		double[][] costs = problem.getCosts();

		assert(costs.length== gene.length);

//		double warehouses = problem.getWarehouses();	// 10
//		for (int i = 0; i < warehouses; i++) {
//			for (int j = 0; j < customers; j++) {
//				if(gene[j]-1 == i){
//					fitness += costs[j][i];
//				}
//			}
//		}
//		
		// get the costs from customer i to its warehouse (gene[i])
		for (int i = 0; i < customers; i++) {
			fitness += costs[i][gene[i]];
			p(costs[i][gene[i]]);
		}

		p(fitness);
		
		
//		double temp1 = fitness;
		
		// add fixcosts
		double[] fixcosts = problem.getFixcosts();
		
		Collection<Integer> warehouseSet = new HashSet<Integer>();
		for (int g : gene) {
			warehouseSet.add(g);
		}
		
		for (Integer currentWareHouse : warehouseSet) {
			fitness += fixcosts[currentWareHouse];
		}
		
//		System.out.println("Kosten: " + (fitness - temp1));
		
//		this.fitness = fitness;
		p(fitness);
	}

	
	
	@Override
	public String toString() {
		if(gene==null){
			return "Gene is null";
		}
		StringBuilder geneString = new StringBuilder();
		for (int i = 0; i < gene.length; i++) {
			geneString.append(gene[i]);
			geneString.append(",");
		}
		return geneString.toString();
	}

	// Getter	////////////////////////////////////////////////////////////////////////////////////
	public int[] getGene() {
		return gene;
	}

	public Problem getProblem() {
		return problem;
	}
	public Double getFitness(){
		if(fitness == null && gene != null){
			calculateFitness();
		}
		return fitness;
	}

	// Implements Interface Methods	////////////////////////////////////////////////////////////////////
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * Compares this object with the specified object for order. 
	 * Returns a negative integer, zero, or a positive integer as this object 
	 * is less than, equal to, or greater than the specified object.
	 */
	public int compareTo(Individual o) {
		return this.getFitness().compareTo(o.getFitness());
	}
	

}
