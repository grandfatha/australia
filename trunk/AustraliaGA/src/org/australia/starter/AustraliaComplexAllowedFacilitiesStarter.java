package org.australia.starter;

import java.util.ArrayList;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.australia.algorithm2.Criterion;
import org.australia.algorithm2.FacilityMutator;
import org.australia.algorithm2.GA;
import org.australia.algorithm2.Individual;
import org.australia.algorithm2.IndividualImpl;
import org.australia.config.Config;
import org.australia.problem.Problem;
import org.australia.problem.ProblemBoccia;
import org.australia.util.Database2;

public class AustraliaComplexAllowedFacilitiesStarter {
	
	private static Logger logger = Logger.getRootLogger();

	public static void main(String[] args) {

		logger.addAppender(new ConsoleAppender(new PatternLayout()));

		long start = System.currentTimeMillis();
		
//		Problem problem = ProblemHolmberg.readProblem("p71");
		Problem problem = ProblemBoccia.readProblem("i5050_1.plc");
		
		Config.setFee(300);
		Config.setNewGenerationSize(1.5);

		
		Individual bestValidIndividualFromDatabase = Database2.getBestValidIndividualFromDatabase(problem);
		ArrayList<Integer> allowedFacilities = IndividualImpl.getFacilities(bestValidIndividualFromDatabase.getGene());
		
		for (int i = 0; i < 20; i++) {
			
			
			// mutate
			logger.info(allowedFacilities);
			ArrayList<Integer> newAllowedFacilities = FacilityMutator.mutateFacilites(allowedFacilities, bestValidIndividualFromDatabase.getProblem().getWarehouses());
			logger.info(newAllowedFacilities);
			
			
			Individual bestIndividual = null;
			
			GA ga = new GA(problem, newAllowedFacilities);
			
			bestIndividual = ga.startAlgorithm(50, Criterion.GENERATIONSNOIMPROVEMENTS, 20000);
	
			
			logger.info("Bestes Individuum:");
			logger.info(bestIndividual);
			
	
			if(Config.getWriteToDatabase()){
				Database2.addIndivudual(bestIndividual);
			}
		
		}
		long end = System.currentTimeMillis();

		logger.debug("Ende");
		logger.debug("Dauer: " + (end-start));

		


		
	}


}
