package org.australia.starter;

import java.util.ArrayList;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.australia.algorithm2.IndividualImpl;
import org.australia.algorithm2.Criterion;
import org.australia.algorithm2.GA;
import org.australia.algorithm2.Individual;
import org.australia.config.Config;
import org.australia.problem.Problem;
import org.australia.problem.ProblemHolmberg;
import org.australia.util.Database2;

public class AustraliaSimpleAllowedFacilitiesStarter {
	
	private static Logger logger = Logger.getRootLogger();

	public static void main(String[] args) {

		logger.addAppender(new ConsoleAppender(new PatternLayout()));
		
		Problem problem = ProblemHolmberg.readProblem("p71");
//		Problem problem = ProblemBoccia.readProblem("i5050_1.plc");
		
		Config.setFee(50);
		Config.setNewGenerationSize(1.5);
		Config.setPrintEachTimes(50);
		Config.setOddsMutation(0.1);

		
		Individual bestValidIndividualFromDatabase = Database2.getBestValidIndividualFromDatabase(problem);
		ArrayList<Integer> allowedFacilities = IndividualImpl.getFacilities(bestValidIndividualFromDatabase.getGene());
		
		logger.info(allowedFacilities);
		
		Individual bestIndividual = null;
		
		GA ga = new GA(problem, allowedFacilities);
		
		long start = System.currentTimeMillis();
		bestIndividual = ga.startAlgorithm(50, Criterion.GENERATIONS, 100000);
		long end = System.currentTimeMillis();

		
		logger.info("Bestes Individuum:");
		logger.info(bestIndividual);
		

		if(Config.getWriteToDatabase()){
	//		Database.addIndivudual(bestIndividual);
		}
		
		
		logger.debug("Ende");
		logger.debug("Dauer: " + (end-start));


		
	}


}
