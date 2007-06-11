package org.australia.algorithm;

import org.australia.problem.Problem;

public interface Individual extends Comparable<Individual> {
	public boolean checkConstraints();
	public void calculateFitness();
	public double getFeeCosts();
	public void mutate();
	public Double getFitness();
	public Problem getProblem();
	public int[] getGene();
}