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
		
		Problem problem = ProblemHolmberg.readProblem("problem/p71");
//		Problem problem = ProblemBoccia.readProblem("problem/i50100_1.plc");
		
		GA ga = new GA(problem);
		
		long start = System.currentTimeMillis();
		Individual bestIndividual = ga.startAlgorithm(200, Criterion.TIMENOIMPROVEMENTS, 60);
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
