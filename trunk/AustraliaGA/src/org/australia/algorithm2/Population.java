package org.australia.algorithm2;

public interface Population {

	public Individual getBestIndividual();
	public Individual getBestValidIndividual();
	public Individual getWorstIndividual();
	public Individual getRandomIndividual();
	public Individual getIndividual(int number);
	public Individual getIndividualByRouletteWheel();
	
	public void selectBest(int size);
	
	public boolean add(Individual individual);
	public boolean remove(Individual individual);

	public int getSize();

}
