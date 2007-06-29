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
		problemList.add(ProblemHolmberg.readProblem("p11"));
//		problemList.add(ProblemHolmberg.readProblem("p21"));
//		problemList.add(ProblemHolmberg.readProblem("p31"));
//		problemList.add(ProblemHolmberg.readProblem("p41"));
//		problemList.add(ProblemHolmberg.readProblem("p51"));
//		problemList.add(ProblemHolmberg.readProblem("p61"));

		ArrayList<Double> feeList = new ArrayList<Double>();
		feeList.add(1.0);
		feeList.add(5.0);
		feeList.add(10.0);
		feeList.add(20.0);
		feeList.add(50.0);

		ArrayList<Integer> populationList = new ArrayList<Integer>();
		populationList.add(100);
		populationList.add(200);
		populationList.add(300);
		populationList.add(500);

		ArrayList<Integer> iterationsList = new ArrayList<Integer>();
		iterationsList.add(2000);
		iterationsList.add(5000);
		iterationsList.add(10000);

		int rounds = 10;

		// iterate over problems
		for (Problem problem : problemList) {

			for (int i = 1; i <= rounds; i++) {

				for (Integer population : populationList) {

					for (Integer iterations : iterationsList) {

						for (Double fee : feeList) {
							Config.setFee(fee);


							GA ga = new GA(problem);
							Individual bestIndividual = null;
							bestIndividual = ga.startAlgorithm(population, Criterion.ITERATIONS, iterations);

							System.out.println("Bestes Individuum:");
							System.out.println(bestIndividual);

							Database.addIndivudualBenchmark(bestIndividual, benchmark, ga.getDuration(), ga.getDurationUntilBestWasFound(), iterations, population);

						} //fee

					} // iterations

				} // population

			} //rounds

		} // Problem

		System.out.println("Ende");
	}
}
