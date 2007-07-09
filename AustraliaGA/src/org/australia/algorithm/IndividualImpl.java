package org.australia.algorithm;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import org.australia.config.Config;
import org.australia.problem.*;
import org.australia.util.Utils;

public class IndividualImpl implements Comparable<Individual>, Individual {

	private int[] gene;		// size: Number of customers; int[0] = 123 --> customer 0 gets goods from warehouse 123

	private Double fitness;
	private boolean changed = true;	// flag -> we do'nt have to calc fitness each time
	
	private Problem problem;
	
	
	
	
	
	
	// Constructor		//////////////////////////////////////////////////////////////////////////
	public IndividualImpl(Problem problem) {
		this.problem = problem;
	}
	
	public IndividualImpl(Problem problem,  int[] gene) {
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
			throw new RuntimeException("Problem or gene is null");
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
	 * Generate a greedy Individual
	 * @param problem
	 * @return Individual
	 * @author benjamin
	 */
	public static Individual generateGreedyIndividual(Problem problem){

		IndividualImpl result = new IndividualImpl(problem);
		
		// number of customers
		int numberOfCustomers = (int)problem.getCustomers();
		
		// generate an empty gene
		result.gene = new int[numberOfCustomers];
		
		// get array with [customer][position] where position=0 defines the position of the closest facility 
		int[][] sortedCosts = problem.getSortedCosts();

		// first take a random customer who can pick the cheapest, possible facility from his sortedCostsList
		
		// fill gene with "full"
		int full = -1;
		for (int i = 0; i < numberOfCustomers; i++) {
			result.gene[i] = full;
		}

		int currentCustomer = 0;

		// fill gene
		for (int i = 0; i < numberOfCustomers; i++) {
			
			// get a random customer thats hasn't been assigned a facility yet
			boolean foundFreeCustomer = false;
			while (!foundFreeCustomer){
				currentCustomer = (int) (Math.random() * numberOfCustomers);
				if (result.gene[currentCustomer] == full){
					foundFreeCustomer = true;
				}
			}
			
			// assign free facility to customer thats next to him
			boolean validFacility = false;
			int position = 0;
			int currentFacility = -2;
			while(!validFacility){	//TODO Gefahr einer endlosschleife, wenn falsche reihenfolge!!!!!!!!!!!!!!!!!!

//				System.out.println("currentCustomer: " + currentCustomer);
//				System.out.println("position: " + position);
				
				// get next best facility
				currentFacility = sortedCosts[currentCustomer][position];

//				System.out.println("current Facility: " + currentFacility);
				
				// check whether capacity of location is sufficant
				double currentCap = problem.getCap()[currentFacility];
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
			// set facility to customer
			result.gene[currentCustomer] = currentFacility;
		}
		return result;
	}
	
	
	
	
	/**
	 * Generate a greedy Individual select the facility by a roulette wheele algorithm
	 * @param problem
	 * @return Individual
	 * @author benjamin, jochen
	 */
	public static Individual generateGreedyIndividualWithRouletteWheel(Problem problem){

		IndividualImpl result = new IndividualImpl(problem);
		
		// number of customers
		int numberOfCustomers = (int)problem.getCustomers();
		
		// generate an empty gene
		result.gene = new int[numberOfCustomers];
		
		// get array with [customer][position] where position=0 defines the position of the closest facility 
		int[][] sortedCosts = problem.getSortedCosts();

		// first take a random customer who can pick the cheapest, possible facility from his sortedCostsList
		
		// fill gene with "full"
		int full = -1;
		for (int i = 0; i < numberOfCustomers; i++) {
			result.gene[i] = full;
		}

		int currentCustomer = 0;

		// fill gene
		for (int i = 0; i < numberOfCustomers; i++) {
			
			// get a random customer thats hasn't been assigned a facility yet
			boolean foundFreeCustomer = false;
			while (!foundFreeCustomer){
				currentCustomer = (int) (Math.random() * numberOfCustomers);
				if (result.gene[currentCustomer] == full){
					foundFreeCustomer = true;
				}
			}
			
			// assign free facility to customer thats next to him
			boolean validFacility = false;
			int position = 0;
			int currentFacility = -2;
			while(!validFacility){	//TODO Gefahr einer endlosschleife, wenn falsche reihenfolge!!!!!!!!!!!!!!!!!!

				position = Utils.rouletteWheel((int)problem.getWarehouses());	// lowest postion has higher chance

				currentFacility = sortedCosts[currentCustomer][position];

//				System.out.println("current Facility: " + currentFacility);
				
				// check whether capacity of location is sufficant
//				double currentCap = problem.getCap()[currentFacility];
//				for(int j = 0; j < result.gene.length; j++){
//					if(result.gene[j] == currentFacility){
//						currentCap = currentCap - problem.getNeeds()[j];
//					}
//				}
				
				//dont check if they are valid - we have fees for it
//				if (currentCap >= problem.getNeeds()[currentCustomer]){
					validFacility = true;
//				}
			}
			// set facility to customer
			result.gene[currentCustomer] = currentFacility;
		}
		return result;
	}
	

	
	/**
	 * Warehouses are in the range from 0 to #warehouses-1
	 * @param warehouses
	 * @return a random warehousenumber
	 */
	private static int getRandomWarehouse(double warehouses){
		return ((int) (Math.random()*warehouses));	// 0 .. ;warehouses-1
	}

	
	// Instance methods 		////////////////////////////////////////////////////////////////////////////////

	
	/**
	 * Check constraints
	 * 
	 * Current constraints are checked
	 * - every customer gets goods from (only) one warehouse (implicit)
	 * - warehouse max. capacity
	 *
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
		double customers = problem.getCustomers();
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
		
		
		/* panalty fees	**************************************************************/
		
		f += getFeeCosts();
		
		this.fitness = f;
	}
	
	public double getFeeCosts(){
		double feecosts =0.0;
		
		// panalty fees
		double fee = Config.getFee(); 	// fee for one units thats more in warehouse than possible
		
		double[] assigned = new double[(int) getProblem().getWarehouses()];
		double[] need = getProblem().getNeeds();
		double[] cap = getProblem().getCap();
		
		for (int i = 0; i < gene.length; i++) {
			// i: number of customer
			//			need[i]	// Need of customer i (0..n-1)
			//			gene[i] // Number of facilities for customer i (0..n-1)
			//			cap[j]  // capacity of warehouse j (0..n-1)

			assigned[gene[i]] += need[i];
		}
		
		// each unit in assgigned > cap
		for(int i=0; i<assigned.length;i++){
			if(assigned[i] > cap[i] ){
				feecosts += (assigned[i] - cap[i] ) * fee;
			}
		}
		
		return feecosts;
		
	}
	
	
	//TODO make less random
	//TODO daniel wants a true mutation
	public void mutate(){
		
		int randomCustomer = (int)(Math.random() * gene.length);	// 0 .. n-1
		
		int randomFacility = (int)(Math.random() * getProblem().getWarehouses());	// 0 .. n-1
		
		gene[randomCustomer] = randomFacility;
		
		this.changed = true;
		
	}
	
	public void mutateSwitchCustomers(){
		
		int randomCustomer1 = (int)(Math.random() * gene.length);	// 0 .. n-1
		int randomCustomer2 = (int)(Math.random() * gene.length);	// 0 .. n-1
		
		int temp = gene[randomCustomer1];
		gene[randomCustomer1] = gene[randomCustomer2];
		gene[randomCustomer2] = temp;
		
		this.changed = true;

	}
	
	public void mutateOnlyCurrentFacilities(){
		// add all used facilities to a set
		HashSet<Integer> facilitySet = new HashSet<Integer>();
		for (int g : gene) {
			facilitySet.add(g);
		}
		
		// get a random facility
		Iterator<Integer> iterator = facilitySet.iterator();
		Integer randomFacility = iterator.next();
		for(int i=0; i<(int)(Math.random()*facilitySet.size()); i++){
			randomFacility = iterator.next();
		}
		
		// mutate a random customer with this random facility
		int randomCustomer = (int)(Math.random() * gene.length);	// 0 .. n-1
		
		gene[randomCustomer] = randomFacility;
		
		this.changed = true;

	}
	

	
	public void mutateCloseAndOpenAFacility(){

		// add all used warehouses to a set
		HashSet<Integer> warehouseSet = new HashSet<Integer>();
		for (int g : gene) {
			warehouseSet.add(g);
		}
		
		// remove a random warehouse from this list
		int size = warehouseSet.size();
		Iterator<Integer> iterator = warehouseSet.iterator();
		Integer facilityToDelete = iterator.next();
		for(int i=0; i<(int)(Math.random()*size); i++){
			facilityToDelete = iterator.next();
		}
		warehouseSet.remove(facilityToDelete);
		
		// add a new random facility
		warehouseSet.add((int)(Math.random()*size));
		
		// replace facilities
		for(int i=0; i<gene.length; i++){
			while(!warehouseSet.contains(gene[i])){
				int position = Utils.rouletteWheel((int)getProblem().getWarehouses());	// lowest postion has higher chance
				gene[i] = this.problem.getSortedCosts()[i][position];	// replace facility with new
			}
			
		}
		this.changed = true;

	}
	

	
	public void mutateNearNeighbor(){
		
		int randomCustomer = (int)(Math.random() * gene.length);	// 0 .. n-1

		int position = Utils.rouletteWheel((int)problem.getWarehouses());	// lowest postion has higher chance
		
		gene[randomCustomer] = this.problem.getSortedCosts()[randomCustomer][position];
		
		this.changed = true;
		
	}
	
	
	
	public void mutateBanFacility(){
		// this facility will be banned
		int randomFacility = (int)(Math.random() * getProblem().getWarehouses());	// 0 .. n-1

		for(int i=0; i<gene.length; i++){
			while(gene[i]==randomFacility){
				gene[i] = (int)(Math.random() * getProblem().getWarehouses());
			}
		}
		this.changed = true;
	}

	public void mutateBanFacilityAndFindNewFacilityByRouletteWheel(){
		// this facility will be banned
		int randomFacility = (int)(Math.random() * getProblem().getWarehouses());	// 0 .. n-1

		for(int currentCustomerNr=0; currentCustomerNr<gene.length; currentCustomerNr++){

			while(gene[currentCustomerNr]==randomFacility){
			
				int position = Utils.rouletteWheel((int)this.problem.getWarehouses());	// lowest postion has higher chance
				gene[currentCustomerNr] = this.problem.getSortedCosts()[currentCustomerNr][position];	// replace facility with new
			
			}
			
		}
		this.changed = true;
	}
	
	public void mutateBanFacilityAndFindNewFromCurretUsed(){		
		
		// add all used warehouses to a set
		HashSet<Integer> warehouseSet = new HashSet<Integer>();
		for (int g : gene) {
			warehouseSet.add(g);
		}
		
		// remove a random warehouse from this list
		int size = warehouseSet.size();
		Iterator<Integer> iterator = warehouseSet.iterator();
		Integer facilityToDelete = iterator.next();
		for(int i=0; i<(int)(Math.random()*size); i++){
			facilityToDelete = iterator.next();
		}
		warehouseSet.remove(facilityToDelete);
		
		for(int i=0; i<gene.length; i++){
			while(!warehouseSet.contains(gene[i])){
				int position = Utils.rouletteWheel((int)getProblem().getWarehouses());	// lowest postion has higher chance
				gene[i] = this.problem.getSortedCosts()[i][position];	// replace facility with new
			}
			
		}
		this.changed = true;
	}



	
	public Individual haveSex(Individual partner){

		Individual baby = null;
		
		int[] thisGene = this.getGene().clone();

		int[] babyGene = new int[thisGene.length];

		Random random = new Random();

		// iterate over gene
		for (int i = 0; i < thisGene.length; i++) {
			
			if(random.nextBoolean()){
				
				babyGene[i] = thisGene[i];
				
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
		geneString.deleteCharAt(geneString.lastIndexOf(","));
		
		geneString.append(" Facility Utilization: ");
		geneString.append(getFacilityUtilizationString());
		return geneString.toString();
	}
	
	public String getGeneString(){
		StringBuilder geneString = new StringBuilder();
		for (int i = 0; i < gene.length; i++) {
			geneString.append(gene[i]);
			geneString.append(",");
		}
		geneString.deleteCharAt(geneString.lastIndexOf(","));
		return geneString.toString();
	}
	
	public String getFacilityUtilizationString(){
		Collection<Integer> warehouseSet = new HashSet<Integer>();
		for (int g : gene) {
			warehouseSet.add(g);
		}

		
		StringBuilder utilizationString = new StringBuilder();
		for (Integer facility : warehouseSet) {
			utilizationString.append(facility);
			utilizationString.append(": ");
			double sum=0.0;
			for(int i=0; i<gene.length; i++){
				if(gene[i]==facility.intValue()){
					sum+=problem.getNeeds()[i];
				}
			}
			utilizationString.append(sum);
			utilizationString.append(" (");
			utilizationString.append(problem.getCap()[facility]);
			utilizationString.append("), ");
		}
		utilizationString.deleteCharAt(utilizationString.lastIndexOf(","));
		utilizationString.deleteCharAt(utilizationString.lastIndexOf(" "));
		
		return utilizationString.toString();
	}
	
	public boolean isValid(){
		if(getFeeCosts() > 0.0){
			return false;
		}
		return true;
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
	
	//	 Setter	////////////////////////////////////////////////////////////////////////////////////
	public void setGene(int[] gene) {
		this.gene = gene;
		this.changed = true;
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
