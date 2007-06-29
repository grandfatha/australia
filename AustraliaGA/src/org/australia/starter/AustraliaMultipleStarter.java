package org.australia.starter;

import java.util.ArrayList;

import org.australia.algorithm.Criterion;
import org.australia.algorithm.GA;
import org.australia.algorithm.Individual;
import org.australia.config.Config;
import org.australia.problem.Problem;
import org.australia.problem.ProblemBoccia;
import org.australia.problem.ProblemHolmberg;
import org.australia.util.Database;

public class AustraliaMultipleStarter {

	public static void main(String[] args) {
		
		ArrayList<Problem> problems = new ArrayList<Problem>();
		
		if(false){
			for(int i=10; i<=71; i++){
				if(i!=26&&i!=47){	// problems with these files
					problems.add(ProblemHolmberg.readProblem("p" + i));
				}
			}
		}else{
			problems.add(ProblemBoccia.readProblem("i50100_1.plc"));
			
		}
		double standardFee = Config.getFee();
		
		while (true){
			
			Config.setFee(standardFee);
			
			Individual individual;
			
			Problem problem = problems.get((int)(Math.random()*problems.size()));


			do{
				GA ga = new GA(problem);
				
				
				individual = ga.startAlgorithm(200, Criterion.TIMENOIMPROVEMENTS, 180);
				
				if(!individual.isValid()){
					if(Config.getWriteToDatabase()){
						Database.addIndivudual(individual);
					}
					System.out.println("Individual is not valid -> Increase fee costs");
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
