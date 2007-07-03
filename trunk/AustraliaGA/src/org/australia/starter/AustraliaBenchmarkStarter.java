package org.australia.starter;

import java.util.ArrayList;

import org.australia.algorithm.Criterion;
import org.australia.algorithm.GA;
import org.australia.algorithm.Individual;
import org.australia.config.Config;
import org.australia.problem.Problem;
import org.australia.problem.ProblemBoccia;
import org.australia.problem.ProblemHolmberg;
import org.australia.util.Database;

/**
 * Test Problems p1, p11, p21, p31, p41, p51, p61
 * each with fee costs: 1, 5, 10, 20, 50
 * each with population size: 100, 200, 300, 500
 * each with iterations: 2000, 5000, 10000
 * each ten times
 *
 * write to database
 * save duration until best was found
 * save duration until algo terminats
 * @author jochen
 *
 */
public class AustraliaBenchmarkStarter {

	public static void main(String[] args) {

		String benchmark = "benchP11";

		ArrayList<Problem> problemList = new ArrayList<Problem>();
//		problemList.add(ProblemHolmberg.readProblem("p1"));
//		problemList.add(ProblemHolmberg.readProblem("p11"));
//		problemList.add(ProblemHolmberg.readProblem("p21"));
//		problemList.add(ProblemHolmberg.readProblem("p31"));
//		problemList.add(ProblemHolmberg.readProblem("p41"));
//		problemList.add(ProblemHolmberg.readProblem("p51"));
		problemList.add(ProblemHolmberg.readProblem("p61"));

		ArrayList<Double> feeList = new ArrayList<Double>();
//		feeList.add(1.0);
//		feeList.add(5.0);
		feeList.add(10.0);
//		feeList.add(20.0);
//		feeList.add(50.0);

		ArrayList<Integer> populationList = new ArrayList<Integer>();
//		populationList.add(10);
//		populationList.add(30);
//		populationList.add(50);
		populationList.add(100);
//		populationList.add(200);
//		populationList.add(300);
//		populationList.add(500);

		ArrayList<Integer> iterationsList = new ArrayList<Integer>();
		iterationsList.add(500);
		iterationsList.add(1000);
		iterationsList.add(2000);
		iterationsList.add(5000);
		iterationsList.add(10000);
		iterationsList.add(20000);
		iterationsList.add(50000);

		int rounds = 10;
		

		// iterate over problems
		for (Problem problem : problemList) {

			for (Integer population : populationList) {

				for (Integer iterations : iterationsList) {

					for (Double fee : feeList) {
						Config.setFee(fee);

						double cumulatedFitness=0.0;
						long timeStarted = System.currentTimeMillis();
						int currentIteration = 0;
						long cumulatedDurationUntilBestWasFound=0;

						System.out.println("Problem: " + problem.getInstanceName() +", Population Size: " + population +", Iterations: "+iterations+", Fees: "+fee);

						
						for (int i = 1; i <= rounds; i++) {

							currentIteration++;

							GA ga = new GA(problem);
							Individual bestIndividual = null;
							bestIndividual = ga.startAlgorithm(population, Criterion.ITERATIONS, iterations);

//							System.out.println("Bestes Individuum:");
//							System.out.println(bestIndividual);
							
							cumulatedFitness+=bestIndividual.getFitness();
							cumulatedDurationUntilBestWasFound+=ga.getDurationUntilBestWasFound()*1000;
							
							if(Config.getWriteToDatabase()){
								Database.addIndivudualBenchmark(bestIndividual, benchmark, ga.getDuration(), ga.getDurationUntilBestWasFound(), iterations, population);
							}

						} // iterations
						
						System.out.println("Durchschnittliche Fitness:\t" + (cumulatedFitness/currentIteration));
						System.out.println("Durchschnittliche Dauer:\t" + ((System.currentTimeMillis()-timeStarted)/currentIteration));
						System.out.println("Durchschnittliche Dauer best:\t" + (cumulatedDurationUntilBestWasFound/currentIteration));
						System.out.println();
						

					} //fee

				} // population

			} //rounds

		} // Problem
		

		System.out.println("Ende");
	}
}
