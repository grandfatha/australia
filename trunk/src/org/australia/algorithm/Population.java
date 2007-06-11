package org.australia.algorithm;

import java.util.SortedSet;

import org.australia.problem.Problem;

public interface Population {

	public SortedSet<Individual> getIndividuals();
	public Problem getProblem();

//	public void initialize(int amount);
	public void evolve();
	public void evolveRoulette();
	
	public Individual getBestIndividual();
	public Individual getWorstIndividual();
	public Individual getRandomIndividual();
	public Individual getIndividual(int number);
	public int getSize();
	public boolean add(Individual individual);
	public boolean remove(Individual individual);
	public void selectBestHalf();
}
