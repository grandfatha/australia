package org.australia.algorithm;

import java.util.SortedSet;

import org.australia.problem.Problem;

public interface Population {

//	public Problem getProblem();
	
	public Individual getBestIndividual();
	public Individual getWorstIndividual();
	public Individual getRandomIndividual();
	public Individual getIndividual(int number);
	public Individual getIndividualByRouletteWheel();
	
	public void selectBestHalf();

	public int getSize();
	public boolean add(Individual individual);
	public boolean remove(Individual individual);
	
}
