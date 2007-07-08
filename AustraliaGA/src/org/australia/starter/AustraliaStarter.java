package org.australia.starter;

import java.util.ArrayList;
import java.util.Collection;

import org.australia.algorithm.GA;
import org.australia.algorithm.Individual;
import org.australia.config.Config;
import org.australia.problem.Problem;
import org.australia.problem.ProblemHolmberg;
import org.australia.util.Database;

public class AustraliaStarter {

	public static void main(String[] args) {


		Problem problem = ProblemHolmberg.readProblem("p2");
//		problem.getSortedCosts();
//		Problem problem = ProblemBoccia.readProblem("i50100_1.plc");
		
		Collection<Individual> ergebnisse = new ArrayList<Individual>();
		
		GA ga = new GA(problem);
		
		for(int i=1; i<=10;i++){
			ergebnisse.add(ga.startAlgorithm(200, 5000));
			System.out.println("Ende Durchgang " + i);
		}
		
		System.out.println("Beste Ergebnisse:");

		
		
		for (Individual individual : ergebnisse) {
			
			if(Config.getWriteToDatabase()){
				Database.addIndivudual(individual);
			}
			
			
			
		}
		
		System.out.println("Ende");


		
		
	}


}
