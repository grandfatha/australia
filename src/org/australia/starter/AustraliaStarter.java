package org.australia.starter;
import org.australia.algorithm.PopulationImpl;
import org.australia.problem.Problem;
import org.australia.problem.ProblemHolmberg;


public class AustraliaStarter {

	public static void main(String[] args) {
		
		Problem problem = ProblemHolmberg.readProblem("problem/p67");
		
		PopulationImpl p = new PopulationImpl(problem, 100);
		
		p.evolve();
		
		System.out.println(p.getBestIndividual().getFitness() + " " + p.getBestIndividual());
		
		System.out.println("Ende");


		
	}

}
