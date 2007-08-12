package org.australia.algorithm2;

import java.util.ArrayList;

import org.australia.problem.Problem;

public interface Individual extends Comparable<Individual> {
	public double getFeeCosts();
	
	public void mutate();
	public void mutateBanFacility();
	public void mutateSwitchCustomers();
	
	public Double getFitness();
	public Problem getProblem();
	public int[] getGene();

	public Individual haveSex(Individual partner);
	
	public boolean isValid();
	public String getGeneString();
	
	public ArrayList<Integer> getFacilities();
}