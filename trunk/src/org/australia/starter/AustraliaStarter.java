package org.australia.starter;

import org.australia.algorithm.GA;
import org.australia.problem.Problem;
import org.australia.problem.ProblemBoccia;

public class AustraliaStarter {

	public static void main(String[] args) {
		
//		Problem problem = ProblemHolmberg.readProblem("problem/p67");
		Problem problem = ProblemBoccia.readProblem("problem/i50100_1.plc");
		
		GA ga = new GA(problem);
		
		ga.startAlgorithm(200, 50000);
		
		System.out.println("Ende");


		
	}


}
