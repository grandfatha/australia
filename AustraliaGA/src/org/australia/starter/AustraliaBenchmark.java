package org.australia.starter;

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

public class AustraliaBenchmark {
	
	private static Logger logger = Logger.getRootLogger();

	public static void main(String[] args) {
		
		
		// Properties
		Problem problem = ProblemBoccia.readProblem("i75100_5.plc");
		
		int startPopulation = 100;
		Criterion criterion = Criterion.GENERATIONS;
		int criterionValue = 3000;
		
		Config.setFee(200);
		Config.setNewGenerationSize(2.0);
		Config.setOddsMutation(0.8);
		Config.setPercentageForeignIndividuals(0.0);
		Config.setPercentageGreedy(0.5);
		Config.setSelectionMethod(1);
		
		
		// don't change from here
		
		for(double i=0.2; i<20; i+=0.2){
			
			Config.setFee(i);
	
			logger.addAppender(new ConsoleAppender(new PatternLayout()));
			
			Individual bestIndividual = null;
			
			GA ga = new GA(problem);
			
			logger.info("Start Instance " + problem.getInstanceName());
			long start = System.currentTimeMillis();
			bestIndividual = ga.startAlgorithm(startPopulation, criterion, criterionValue);
			long end = System.currentTimeMillis();
	
			
			logger.info("Bestes Individuum:");
			logger.info(bestIndividual);
			
	
			if(Config.getWriteToDatabase()){
				Database.addIndivudualNew(bestIndividual, ga);
			}
			logger.debug("Dauer: " + (end-start));
			
		}
			
		logger.debug("Ende");


		
	}


}
