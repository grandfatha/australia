package org.australia.starter;

import java.util.ArrayList;
import java.util.Collection;

import org.australia.algorithm.GA;
import org.australia.algorithm.Individual;
import org.australia.problem.Problem;
import org.australia.problem.ProblemHolmberg;

public class AustraliaStarter {

	public static void main(String[] args) {
		
		Problem problem = ProblemHolmberg.readProblem("problem/p2");
//		Problem problem = ProblemBoccia.readProblem("problem/i50100_1.plc");
		
		Collection<Individual> ergebnisse = new ArrayList<Individual>();
		
		GA ga = new GA(problem);
		
		for(int i=1; i<=5;i++){
			ergebnisse.add(ga.startAlgorithm(200, 2000));
			System.out.println("Ende Durchgang " + i);
		}

		System.out.println("Beste Ergebnisse:");

		for (Individual individual : ergebnisse) {
			System.out.println(individual);
		}
		
		System.out.println("Ende");


		
	}


}
