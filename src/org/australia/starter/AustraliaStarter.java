package org.australia.starter;
import org.australia.algorithm.Population;
import org.australia.algorithm.PopulationImpl;
import org.australia.problem.Problem;
import org.australia.problem.ProblemBoccia;
import org.australia.problem.ProblemHolmberg;


public class AustraliaStarter {

	public static void main(String[] args) {
		
//		Problem problem = ProblemHolmberg.readProblem("problem/p67");
		Problem problem = ProblemBoccia.readProblem("problem/i50100_1.plc");
		
		Population p = new PopulationImpl(problem, 1000);

		System.out.println(p.getBestIndividual());
		
//		for(int i=0; i<100; i++){
//			p.evolveRoulette();
//			if(i%10==0){
//				System.out.println(p.getBestIndividual());
//			}
//		}

		p.evolve();
		
		System.out.println(p.getBestIndividual().getFitness() + " " + p.getBestIndividual());
		
		System.out.println("Ende");


		
	}


}
