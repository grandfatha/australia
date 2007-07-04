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
import org.australia.util.Database;

public class AustraliaSimpleStarter {
	
	private static Logger logger = Logger.getRootLogger();

	public static void main(String[] args) {

		logger.addAppender(new ConsoleAppender(new PatternLayout()));
		
//		Problem problem = ProblemHolmberg.readProblem("p31");
		Problem problem = ProblemBoccia.readProblem("i5050_1.plc");
		
		
		Individual bestIndividual = null;
		
		GA ga = new GA(problem);
		
		long start = System.currentTimeMillis();
		bestIndividual = ga.startAlgorithm(200, Criterion.ITERATIONS, 3000);
		long end = System.currentTimeMillis();

		
		logger.info("Bestes Individuum:");
		logger.info(bestIndividual);
		

		if(Config.getWriteToDatabase()){
			Database.addIndivudual(bestIndividual);
		}
		
		
		logger.debug("Ende");
		logger.debug("Dauer: " + (end-start));


		
	}


}
