package org.australia.algorithm;

import java.util.Collection;
import java.util.HashSet;
import org.australia.problem.*;
import org.australia.util.Utils;

public class IndividualImpl implements Comparable<Individual>, Individual {

	private int[] gene;		// size: Number of customers; int[0] = 123 --> customer 0 gets goods from warehouse 123

	private Double fitness;
	private boolean changed = true;	// flag -> we do'nt have to calc fitness each time
	
	private Problem problem;
	
	private static int full = 999;
	
	
	
	
	
	// Constructor		//////////////////////////////////////////////////////////////////////////
	private IndividualImpl(Problem problem) {
		this.problem = problem;
	}
	
	
	private IndividualImpl(Problem problem,  int[] gene) {
		this.problem = problem;
		this.gene = gene;
		this.changed = true;
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
		}
		
		Individual individual = new IndividualImpl(problem, gene);
		
		return individual;
		
	}
	
	/**
	 * Generate a completely random Individual
	 * @param problem
	 * @return Individual
	 * @author jochen
	 */
	public static Individual generateRandomIndividual(Problem problem){

		IndividualImpl result = new IndividualImpl(problem);
		
		// number of customers
		int numberOfCustomers = (int)problem.getCustomers();
		
		// generate an empty gene
		result.gene = new int[numberOfCustomers];

		// number of warehouses
		double warehouses = problem.getWarehouses();
		
		// assign every customer a random warehouse
		//TODO make less random and improve nearest neighbor...
		for (int i = 0; i < numberOfCustomers; i++) {
			result.gene[i] = getRandomWarehouse(warehouses);
		}
		
		return result;
	}
	/**
	 * Generate a costs specific Individual
	 * @param problem
	 * @return Individual
	 * @author benjamin
	 */
	public static Individual generateCostspecificIndividual(Problem problem){

		IndividualImpl result = new IndividualImpl(problem);
		
		// number of customers
		int numberOfCustomers = (int)problem.getCustomers();
		
		// generate an empty gene
		result.gene = new int[numberOfCustomers];

		// number of warehouses
		double warehouses = problem.getWarehouses();
		
//		//copy sorted costs in an local variabl
		int[][] sortedCosts = problem.getSortedCosts();
//		int[][] localCosts = new int[numberOfCustomers][sortedCosts[0].length];
		
//		for (int i = 0; i < sortedCosts.length; i++) {
//			localCosts[i] = sortedCosts[i].clone();
//		}
		// first take a random customer who can pick the cheapest, possible facility from his sortedCostsList
		
		// fill gene with "full"
		
		// fill gene
		for (int i = 0; i < numberOfCustomers; i++) {
			result.gene[i] = full;
		}
		int currentCustomer = 0;	//jc refactored: bad english actual -> current
		// fill gene
		for (int i = 0; i < numberOfCustomers; i++) {
			boolean foundFreeCustomer = false;
			while (!foundFreeCustomer){
				currentCustomer = (int) (Math.random() * numberOfCustomers);
				if (result.gene[currentCustomer] == full){
					foundFreeCustomer = true;
				}
			}
			boolean validFacility = false;
			int position = 0;
			while(!validFacility){
				double[] allCap = problem.getCap();
				System.out.println("currentCustomer: " + currentCustomer);
				System.out.println("position: " + position);
				int currentFacility = sortedCosts[currentCustomer][position];
				System.out.println("current Facility: " + currentFacility);
				double currentCap = allCap[currentFacility];
				for(int j = 0; j < result.gene.length; j++){
					if(result.gene[j] == currentFacility){
						currentCap = currentCap - problem.getNeeds()[j];
					}
				}
				if (currentCap >= problem.getNeeds()[currentCustomer]){
					validFacility = true;
				}else{
					position++;
				}
			}
			result.gene[i] = position;
		}
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


		// get the costs from customer i to its warehouse (gene[i])
		for (int i = 0; i < customers; i++) {
			f += costs[i][gene[i]];
		}
		
		
		// add fixcosts
		double[] fixcosts = problem.getFixcosts();
		boolean[] warehouseTaken = new boolean[(int) getProblem().getWarehouses()];

		for(int i=0; i<gene.length; i++){
			if(!warehouseTaken[gene[i]]){
				f += fixcosts[gene[i]];
				warehouseTaken[gene[i]] = true;
			}
		}
		
		
		// strafkosten / M-Method
		double fee = 1; 	// fee for one units thats more in warehouse than possible
		
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
		double fee = 20; 	// fee for one units thats more in warehouse than possible
		
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
		
		this.changed = true;
		
	}
	
	public void mutateDisallowFacility(){
		int randomWarehouse = (int)(Math.random() * getProblem().getWarehouses());	// 0 .. n-1
		for(int i=0; i<gene.length; i++){
			while(gene[i]==randomWarehouse){
				gene[i] = (int)(Math.random() * getProblem().getWarehouses());
			}
		}
	}
	
	
	public Individual haveSex(Individual partner){

		Individual baby = null;

		int[] babyGene = new int[this.getGene().length];
		
		// random array with true or false
		boolean[] pattern = Utils.getRandomPattern(this.getGene().length);	
	
		// iterate over gene
		for (int i = 0; i < this.getGene().length; i++) {
			
			if(pattern[i]){	// true at posistion i
				
				babyGene[i] = this.getGene()[i];
				
			}else{
				
				babyGene[i] = partner.getGene()[i];
			}
		}
		
		// create baby via factory method
		// return null if invalid
		baby = IndividualImpl.createInstance(this.getProblem(), babyGene);

		if(baby==null){
			throw new RuntimeException("Baby is null");
		}
		
		return baby;
	}

	

	public int getAmountWarehouses(){
		Collection<Integer> warehouseSet = new HashSet<Integer>();
		for (int g : gene) {
			warehouseSet.add(g);
		}
		return warehouseSet.size();
	}

	
	@Override
	public String toString() {
		if(gene==null){
			return "Gene is null";
		}
		StringBuilder geneString = new StringBuilder();
		geneString.append("Fitness: ");
		geneString.append(this.getFitness());
		geneString.append(" Fees: ");
		geneString.append(this.getFeeCosts());
		geneString.append(" Warehouses: ");
		geneString.append(this.getAmountWarehouses());
		geneString.append(" Gene: ");
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
		if(this.changed == true){
			calculateFitness();
			this.changed=false;
		}
		return fitness;
	}

	// Implements Comparable Interface Methods	////////////////////////////////////////////////////////////////////
	
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
