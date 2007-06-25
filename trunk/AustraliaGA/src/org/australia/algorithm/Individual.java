package org.australia.algorithm;

import org.australia.problem.Problem;

public interface Individual extends Comparable<Individual> {
	public boolean checkConstraints();
	public double getFeeCosts();
	
	public void mutate();
	public void mutateBanFacility();
	public void mutateBanFacilityAndFindNewFacilityByRouletteWheel();
	
	public Double getFitness();
	public Problem getProblem();
	public int[] getGene();

	public Individual haveSex(Individual partner);
}