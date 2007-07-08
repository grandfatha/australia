package org.australia.starter;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.australia.algorithm.Criterion;
import org.australia.algorithm.GA;
import org.australia.algorithm.Individual;
import org.australia.algorithm.IndividualImpl;
import org.australia.algorithm.Population;
import org.australia.algorithm.PopulationImpl;
import org.australia.config.Config;
import org.australia.problem.Problem;
import org.australia.problem.ProblemBoccia;
import org.australia.problem.ProblemHolmberg;
import org.australia.util.Database;
import org.australia.util.HolmbergOptimal;

public class AustraliaDatabaseOptimizerStarter {

	private static Logger logger = Logger.getRootLogger();

	public static void main(String[] args) {

		logger.addAppender(new ConsoleAppender(new PatternLayout()));

		
		ArrayList<Problem> problems = new ArrayList<Problem>();
		Population population = null;

		//Define Problem
//		problems.add(ProblemHolmberg.readProblem("p19"));
//		problems.add(ProblemHolmberg.readProblem("p23"));
//		problems.add(ProblemHolmberg.readProblem("p24"));
//		problems.add(ProblemHolmberg.readProblem("p25"));
//		problems.add(ProblemHolmberg.readProblem("p27"));
//		problems.add(ProblemHolmberg.readProblem("p28"));
//		problems.add(ProblemHolmberg.readProblem("p29"));
//		problems.add(ProblemHolmberg.readProblem("p66"));
//		problems.add(ProblemBoccia.readProblem("i5050_1.plc"));
		
		problems.add(ProblemHolmberg.readProblem("p61"));
		
		for (Problem problem : problems) {
			int count = 1;
						
			do{
				if(++count > 50){
					break;
				}
				
				// Create an empty population
				population = new PopulationImpl(problem);
				
				if(Math.random() < 0.7){
		
					// get individuals from database for our problem
					Collection<Individual> individualsFromDatabase = Database.getIndividualsFromDatabase(problem, true, 20);
		
					// add these individuals to our population
					if(individualsFromDatabase!=null){
						for (Individual individual : individualsFromDatabase) {
							population.add(individual);
						}
					}
				}
				
				System.out.println("There are " + population.getSize() +" individuals in the database");
				
				population.add(IndividualImpl.generateGreedyIndividual(problem));
				
				// add some random individuals to population
				while(population.getSize() < 200){
					population.add(IndividualImpl.generateRandomIndividual(problem));
				}
				
				//What is currently the best Individual
				System.out.println(population.getBestIndividual());
				
				// start the ga
				GA ga = new GA(problem);
				Individual bestIndividual = ga.startAlgorithm(population, Criterion.GENERATIONS, 10000);
				
				// Output
				System.out.println("Bestes Individuum:");
				System.out.println(bestIndividual);
				
				// Write new Individual to database
				if(Config.getWriteToDatabase()){
					Database.addIndivudual(bestIndividual);
				}
			
			}while(!(problem instanceof ProblemHolmberg ) || !HolmbergOptimal.isOptimal(population.getBestIndividual()));
	
			logger.info("Optimal Individual was found");
			
		}
		
		logger.info("End");

	}


}
