package org.australia.problem;

public interface Problem {

	public double[] getCap() ;

	/**
	 * this returns the cummulated costs from the customer to the facility
	 * double[customerNr][facilityNr]
	 * @return costs from customer to facility
	 * @author jochen
	 */
	public double[][] getCosts() ;
	public double getCustomers() ;
	public double[] getFixcosts();
	public double[] getNeeds();
	public double getWarehouses();
	public int[][] getSortedCosts();

}