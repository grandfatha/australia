package org.australia.algorithm;

import org.australia.problem.Problem;

public interface Individual extends Comparable<Individual> {
	public boolean checkConstraints();
	public double getFeeCosts();
	
	public void mutate();
	public void mutateBanFacility();
	public void mutateBanFacilityAndFindNewFacilityByRouletteWheel();
	public void mutateBanFacilityAndFindNewFromCurretUsed();
	public void mutateNearNeighbor();
	public void mutateSwitchCustomers();
	public void mutateCloseAndOpenAFacility();
	
	public Double getFitness();
	public Problem getProblem();
	public int[] getGene();

	public Individual haveSex(Individual partner);
	
	public boolean isValid();
	public String getGeneString();
}