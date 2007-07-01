package org.australia.starter;

import java.util.ArrayList;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.australia.algorithm.Individual;
import org.australia.problem.Problem;
import org.australia.problem.ProblemHolmberg;
import org.australia.util.Database;
import org.australia.util.HolmbergOptimal;

public class AustraliaComparator {
	
	private static Logger logger = Logger.getRootLogger();

	public static void main(String[] args) {

		logger.addAppender(new ConsoleAppender(new PatternLayout()));
		logger.setLevel(Level.INFO);

		ArrayList<Problem> problems = new ArrayList<Problem>();

		double cumulatedDeviation = 0.0;
		
		
		
		for(int i=1; i<=71; i++){
			if(i!=26&&i!=47){	// problems with these files
				problems.add(ProblemHolmberg.readProblem("p" + i));
			}
		}
		
		for (Problem problem : problems) {
			
			
			Individual bestValidIndividualFromDatabase = Database.getBestValidIndividualFromDatabase(problem);

			if(bestValidIndividualFromDatabase==null){
				logger.info(problem.getInstanceName() + ": No valid Indivdiual");
				continue;
			}
			
			StringBuilder info = new StringBuilder();
			
			
			Double fitness = bestValidIndividualFromDatabase.getFitness();
			double optimal = HolmbergOptimal.getOptimal(problem);
			double deviation = fitness-optimal;

			info.append("" + problem.getInstanceName() + ": ");
			info.append(fitness);
			info.append("\t");
			info.append(optimal);
			info.append("\t");
			info.append(deviation);
			info.append("\t");
			info.append(deviation/optimal);
			info.append("%");
			
			logger.info(info);
			
			cumulatedDeviation += deviation;

		}
		
		logger.info("Cumulated Deviation: " + cumulatedDeviation);

		
	}

}
