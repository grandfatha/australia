package org.australia.starter;

import java.util.ArrayList;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.australia.algorithm.Individual;
import org.australia.problem.Problem;
import org.australia.problem.ProblemBoccia;
import org.australia.problem.ProblemHolmberg;
import org.australia.util.Database;
import org.australia.util.HolmbergOptimal;

public class AustraliaBocciaResults {
	
	private static Logger logger = Logger.getRootLogger();

	public static void main(String[] args) {

		logger.addAppender(new ConsoleAppender(new PatternLayout()));
		logger.setLevel(Level.INFO);

		ArrayList<Problem> problems = new ArrayList<Problem>();

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

		
		for (Problem problem : problems) {
			
			Individual bestValidIndividualFromDatabase = Database.getBestValidIndividualFromDatabase(problem);

			StringBuilder info = new StringBuilder();
			
			Double fitness = bestValidIndividualFromDatabase.getFitness();

			info.append("" + problem.getInstanceName() + ": ");
			info.append(fitness);
			
			logger.info(info);
			

		}
		

		
	}

}
