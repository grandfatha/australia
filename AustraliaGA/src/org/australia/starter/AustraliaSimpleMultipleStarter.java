package org.australia.starter;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.australia.algorithm.Criterion;
import org.australia.algorithm.Individual;
import org.australia.algorithm.MultipleGA;
import org.australia.config.Config;
import org.australia.problem.Problem;
import org.australia.problem.ProblemBoccia;
import org.australia.util.Database;

public class AustraliaSimpleMultipleStarter {
	
	private static Logger logger = Logger.getRootLogger();

	public static void main(String[] args) {

		logger.addAppender(new ConsoleAppender(new PatternLayout()));
		
		Config.setFee(250);
		
//		Problem problem = ProblemHolmberg.readProblem("p1");
		Problem problem = ProblemBoccia.readProblem("i5050_1.plc");
		
		
		Individual bestIndividual = null;
		
		MultipleGA ga = new MultipleGA(problem);
		
		long start = System.currentTimeMillis();
		bestIndividual = ga.startMultipleAlgorithm(10, 50, Criterion.GENERATIONSNOIMPROVEMENTS, 20000);
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
