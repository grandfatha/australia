package org.australia.algorithm;

import javolution.util.FastComparator;

public class IndividualComparator extends
		FastComparator<Individual> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean areEqual(Individual arg0, Individual arg1) {
		return arg0.getFitness() == arg1.getFitness();
	}

	@Override
	public int compare(Individual arg0, Individual arg1) {
		return arg0.compareTo(arg1);
	}

	@Override
	public int hashCodeOf(Individual arg0) {
		System.out.println("hashCodeOf");
		return arg0.hashCode();
	}

}
