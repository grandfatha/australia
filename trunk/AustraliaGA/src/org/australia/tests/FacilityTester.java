package org.australia.tests;

import java.util.Collection;

import org.australia.algorithm.Individual;
import org.australia.problem.Problem;
import org.australia.problem.ProblemHolmberg;
import org.australia.util.Database;

public class FacilityTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Problem p = ProblemHolmberg.readProblem("p71");

		Collection<Individual> individualsFromDatabase = Database.getIndividualsFromDatabase(p, true, 10);
		
		for (Individual individual : individualsFromDatabase) {
			
			System.out.println(individual);
			
			Collection<Integer> facilities = individual.getFacilities();
			
			for (Integer integer : facilities) {
				System.out.print(integer + " ");
			}
			System.out.println();
		
		}


	}

}
