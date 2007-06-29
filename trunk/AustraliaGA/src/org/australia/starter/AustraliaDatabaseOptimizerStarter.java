package org.australia.starter;

import java.util.Collection;

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

public class AustraliaDatabaseOptimizerStarter {

	public static void main(String[] args) {
		
		//Define Problem
		Problem problem = ProblemBoccia.readProblem("i50100_2.plc");

		while(true){
			
			// Create an empty population
			Population population = new PopulationImpl(problem);
	
			// get individuals from database for our problem
			Collection<Individual> individualsFromDatabase = Database.getIndividualsFromDatabase(problem, true, 1);
	
			// add these individuals to our population
			if(individualsFromDatabase!=null){
				for (Individual individual : individualsFromDatabase) {
					population.add(individual);
				}
			}
			
//			System.out.println("Best individual in database: ");
//			System.out.println(population.getBestIndividual());
			
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
			Individual bestIndividual = ga.startAlgorithm(population, Criterion.ITERATIONS, 10000);
			
			// Output
			System.out.println("Bestes Individuum:");
			System.out.println(bestIndividual);
			
			// Write new Individual to database
			if(Config.getWriteToDatabase()){
				Database.addIndivudual(bestIndividual);
			}
		
		}

//		System.out.println("Ende");

	}


}
