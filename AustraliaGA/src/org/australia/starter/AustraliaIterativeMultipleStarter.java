package org.australia.starter;

import java.util.ArrayList;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.australia.algorithm.Criterion;
import org.australia.algorithm.Individual;
import org.australia.algorithm.MultipleGA;
import org.australia.config.Config;
import org.australia.problem.Problem;
import org.australia.problem.ProblemBoccia;
import org.australia.problem.ProblemHolmberg;
import org.australia.util.Database;

public class AustraliaIterativeMultipleStarter {

	private static Logger logger = Logger.getRootLogger();

	public static void main(String[] args) {

		logger.addAppender(new ConsoleAppender(new PatternLayout()));


		ArrayList<Problem> problems = new ArrayList<Problem>();

		if(true){
			for(int i=24; i<=71; i++){
				if(i!=26&&i!=47){	// problems with these files
					problems.add(ProblemHolmberg.readProblem("p" + i));
				}
			}
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

		for (Problem problem : problems) {

			if (problem instanceof ProblemHolmberg) {
				Config.setFee(10);
			}else{
				Config.setFee(200);
			}

			Individual bestIndividual = null;

			MultipleGA ga = new MultipleGA(problem);

			long start = System.currentTimeMillis();
			bestIndividual = ga.startMultipleAlgorithm(20, 50, Criterion.GENERATIONSNOIMPROVEMENTS, 20000);
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


}
