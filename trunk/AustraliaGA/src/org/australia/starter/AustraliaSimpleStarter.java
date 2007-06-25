package org.australia.starter;

import java.util.ArrayList;
import java.util.Collection;

import org.australia.algorithm.GA;
import org.australia.algorithm.Individual;
import org.australia.problem.Problem;
import org.australia.problem.ProblemHolmberg;

public class AustraliaSimpleStarter {

	public static void main(String[] args) {
		
		Problem problem = ProblemHolmberg.readProblem("problem/p1");
//		Problem problem = ProblemBoccia.readProblem("problem/i50100_1.plc");
		
		GA ga = new GA(problem);
		
		Individual bestIndividual = ga.startAlgorithm(200, 2000);

		System.out.println("Bestes Individuum:");
		System.out.println(bestIndividual);
		
		System.out.println("Ende");


		
	}


}
