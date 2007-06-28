package org.australia.starter;

import org.australia.algorithm.Criterion;
import org.australia.algorithm.GA;
import org.australia.algorithm.Individual;
import org.australia.config.Config;
import org.australia.problem.Problem;
import org.australia.problem.ProblemHolmberg;
import org.australia.util.Database;

public class AustraliaSimpleStarter {

	public static void main(String[] args) {
		
		Problem problem = ProblemHolmberg.readProblem("problem/p1");
//		Problem problem = ProblemBoccia.readProblem("problem/i50100_1.plc");
		
		
		Individual bestIndividual = null;
		
		GA ga = new GA(problem);
		
		long start = System.currentTimeMillis();
		bestIndividual = ga.startAlgorithm(300, Criterion.ITERATIONS, 3000);
		long end = System.currentTimeMillis();

		
		System.out.println("Bestes Individuum:");
		System.out.println(bestIndividual);
		

		if(Config.getWriteToDatabase()){
			Database.addIndivudual(bestIndividual);
		}
		
		
		
		System.out.println("Ende");
		System.out.println("Dauer: " + (end-start));


		
	}


}
