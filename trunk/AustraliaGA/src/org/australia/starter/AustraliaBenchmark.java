package org.australia.starter;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.australia.algorithm.Criterion;
import org.australia.algorithm.GA;
import org.australia.algorithm.Individual;
import org.australia.config.Config;
import org.australia.problem.Problem;
import org.australia.problem.ProblemHolmberg;
import org.australia.util.Database;

public class AustraliaBenchmark {
	
	private static Logger logger = Logger.getRootLogger();

	public static void main(String[] args) {
		
		
		// Properties
		Problem problem = ProblemHolmberg.readProblem("p43");
		
		int startPopulation = 200;
		Criterion criterion = Criterion.GENERATIONS;
		int criterionValue = 1000;
		
		Config.setFee(5);
		Config.setNewGenerationSize(2.0);
		Config.setOddsMutation(0.8);
		Config.setPercentageForeignIndividuals(0.0);
		Config.setPercentageGreedy(0.5);
		Config.setSelectionMethod(1);
		
		
		// don't change from here

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
		
		
		logger.debug("Ende");
		logger.debug("Dauer: " + (end-start));


		
	}


}
