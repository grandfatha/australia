package org.australia.starter;

import java.util.ArrayList;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.australia.algorithm.Criterion;
import org.australia.algorithm.GA;
import org.australia.algorithm.Individual;
import org.australia.config.Config;
import org.australia.problem.Problem;
import org.australia.problem.ProblemBoccia;
import org.australia.problem.ProblemHolmberg;
import org.australia.util.Database;

public class AustraliaMultipleStarter {

	private static Logger logger = Logger.getRootLogger();

	public static void main(String[] args) {

		logger.addAppender(new ConsoleAppender(new PatternLayout()));
		
		ArrayList<Problem> problems = new ArrayList<Problem>();
		
		if(true){
			for(int i=23; i<=71; i++){
				if(i!=26&&i!=47){	// problems with these files
					problems.add(ProblemHolmberg.readProblem("p" + i));
				}
			}
//		}else{
			for(int i=1; i<=15; i++)
				problems.add(ProblemBoccia.readProblem("i5050_"+i+".plc"));
			for(int i=1; i<=15; i++)
				problems.add(ProblemBoccia.readProblem("i5075_"+i+".plc"));
			for(int i=1; i<=15; i++)
				problems.add(ProblemBoccia.readProblem("i50100_"+i+".plc"));
			for(int i=1; i<=15; i++)
				problems.add(ProblemBoccia.readProblem("i75100_"+i+".plc"));
			for(int i=1; i<=15; i++)
				problems.add(ProblemBoccia.readProblem("i7575_"+i+".plc"));
			
		}
		double standardFee = Config.getFee();
		
		while (true){
			
			Config.setFee(standardFee);
			
			Individual individual;
			
			Problem problem = problems.get((int)(Math.random()*problems.size()));


			do{
				GA ga = new GA(problem);
				
				
				individual = ga.startAlgorithm(100, Criterion.ITERATIONS, 10000);
				
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
