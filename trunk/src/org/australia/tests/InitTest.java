package org.australia.tests;

import org.australia.algorithm.Population;
import org.australia.algorithm.PopulationImpl;
import org.australia.problem.Problem;
import org.australia.problem.ProblemHolmberg;

public class InitTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Problem problem = ProblemHolmberg.readProblem("problem/p67");
		
		PopulationImpl p = new PopulationImpl(problem, 100);
		
		for(int i=0; i<p.getIndividuals().size();i++){
			System.out.println(p.getIndividual(i).getFitness() + " " + p.getIndividual(i));
		}

	}

}
