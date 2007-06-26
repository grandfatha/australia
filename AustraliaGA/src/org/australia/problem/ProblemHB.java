package org.australia.problem;

abstract class ProblemHB implements Problem{
	double warehouses;
	double customers;
	double[] needs;
	double[] cap;
	double[] fixcosts;	// Eröffnungskosten für warehouse
	double[][] costs;   // transportkosten
	int[][] sortedCosts;
	static ProblemHB result;
	String instanceName;
	
	
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
	
	public String getInstanceName(){
		return instanceName;
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
	
	@Override
	public String toString() {
		return "Warehouses: " + warehouses + ", Customers: " + customers;
	}
	
	
	

}
