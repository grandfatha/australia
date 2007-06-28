package org.australia.starter;

import java.util.ArrayList;

import org.australia.algorithm.GA;
import org.australia.algorithm.Individual;
import org.australia.config.Config;
import org.australia.problem.Problem;
import org.australia.problem.ProblemHolmberg;
import org.australia.util.Database;

public class AustraliaMultipleStarter {

	public static void main(String[] args) {
		
		ArrayList<Problem> problems = new ArrayList<Problem>();
		
		for(int i=10; i<=71; i++){
			if(i!=26&&i!=47){	// problems with these files
				problems.add(ProblemHolmberg.readProblem("problem/p" + i));
			}
		}
		
		double standardFee = Config.getFee();
		
		while (true){
			
			Config.setFee(standardFee);
			
			Individual individual;
			
			Problem problem = problems.get((int)(Math.random()*problems.size()));


			do{
				GA ga = new GA(problem);
				individual = ga.startAlgorithm(200, 5000);
				
				if(!individual.isValid()){
					Config.setFee(Config.getFee()*2);
				}
			
			}while(!individual.isValid());
			
			if(Config.getWriteToDatabase()){
				Database.addIndivudual(individual);
			}

			System.out.println("Ende von Problem " + problem.getInstanceName() );
			
		}
		
//		System.out.println("Ende");

	}


}
