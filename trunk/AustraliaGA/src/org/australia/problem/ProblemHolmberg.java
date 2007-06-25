package org.australia.problem;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;

import org.australia.algorithm.Individual;
import org.australia.algorithm.IndividualImpl;

/**
 * @author jochen
 * Problem represents a "australia" problem
 * 
 *
 */

public class ProblemHolmberg implements Problem {
	private double warehouses;
	private double customers;
	private double[] needs;
	private double[] cap;
	private double[] fixcosts;	// Eröffnungskosten für warehouse
	private double[][] costs;   // transportkosten
	private int[][] sortedCosts;

	


	public double[] getCap() {
		return cap;
	}


	public double[][] getCosts() {
		return costs;
	}


	public double getCustomers() {
		return customers;
	}


	public double[] getFixcosts() {
		return fixcosts;
	}


	public double[] getNeeds() {
		return needs;
	}


	public double getWarehouses() {
		return warehouses;
	}


	public static ProblemHolmberg readProblem(String s){
		File f = new File(s);
		return readProblem(f);
	}
	

	/**
	 * sort costs for each customer and its facilities
	 * @author benjamin
	 */
	public int[][] getSortedCosts(){
		
		if(sortedCosts!=null){
			return sortedCosts;
		}

		// eventeuell kann man in die Berechnung noch die Anteiligen Kosten des Bedarfs an
		// den Fixkosten des jeweiligen Lagers hinzunehmen
		double[][] costs = this.getCosts();
		sortedCosts = new int[costs.length][costs[0].length];
		double lowestCosts;
		int position;

			// create an array with customers and all warehouses, the warehouses with the lowest costs are 
			// at a lower position in this array
			for (int i = 0; i < costs.length; i++) {
				double[] costsForEachCustomer = costs[i].clone();
				lowestCosts = 0;
				position = 0;
				for (int j = 0; j < costsForEachCustomer.length; j++) {
					lowestCosts = costsForEachCustomer[j];
					position = j;
					for (int z = 0; z < costsForEachCustomer.length; z++){
						double currentCosts = costsForEachCustomer[z];
						if(lowestCosts > currentCosts){
							lowestCosts = currentCosts;
							position = z;
						}
					}
					costsForEachCustomer[position]= 999999.9;
					sortedCosts[i][j] = position;	
				}
			}

//			System.out.println("Ergebnisse zeigen: ");
//			// show this result
//			for (int i = 0; i < sortedCosts.length; i++){
//				int[] sortedWarehouses = sortedCosts[i];
//				System.out.print("Customer " + i + ": ");
//				for (int j = 0; j < sortedWarehouses.length; j++) {
//					int warehouseNumber = sortedCosts[i][j];
//					System.out.print(" " + warehouseNumber + " |");	
//				}
//				System.out.println();
//			}

		return sortedCosts;
	}
	
	
	/**
	 * Factory to generate a new instance of problem from a given file
	 * 
	 * some code was adapted from Ulrich Sperlich
	 * 
	 * @author benjamin
	 * @param f
	 * @return new Problem instance
	 */
	public static ProblemHolmberg readProblem(File f){
	ProblemHolmberg result = new ProblemHolmberg();
	
	
	// adapted from Ulrich Sperlich
    StreamTokenizer st;
	int iteration = 0;
	int facility = 0;
	int needs = 0;
	int customer = 0;
	try {
		st = new StreamTokenizer(new FileReader(f));                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
		st.slashStarComments( true );
		st.ordinaryChar( '/' );
		st.parseNumbers();
		st.eolIsSignificant( true );

		for ( int tval; (tval = st.nextToken()) != StreamTokenizer.TT_EOF; ) {
			if ( tval == StreamTokenizer.TT_NUMBER ){
				switch (iteration) {
				case 0:
					result.warehouses = st.nval;
					break;
					
				case 1:
		    		  result.customers=st.nval;
		    		  result.fixcosts=new double[(int)result.warehouses];
		    		  result.cap=new double[(int)result.warehouses];
		    		  result.costs=new double[(int)result.customers][(int)result.warehouses];
		    		  result.needs=new double[(int)result.customers];
		    		  break;
					
				default:
					if (iteration < ( result.warehouses*2+2) ){
						if(iteration%2 == 0){
							result.fixcosts[facility] = st.nval;
							break;
						}else{
							result.cap[facility] = st.nval;
							facility++;
							break;
						}						
					}else if (iteration < (( result.warehouses*2+2) + result.customers) ){
						result.needs[needs] = st.nval;
						needs++;
						facility = 0;
						customer = 0;
						break;
					}else{
						if (customer == result.customers){
							facility ++;
							customer = 0;
						}
						 result.costs[customer][facility] = st.nval;
						 customer++;
						 break;
					}
				}
				iteration++;
			}	
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		return "Warehouses: " + warehouses + ", Customers: " + customers;
	}
	

}
