package org.australia.starter;

import java.util.ArrayList;

import org.australia.algorithm.GA;
import org.australia.problem.Problem;
import org.australia.problem.ProblemHolmberg;
import org.australia.util.Database;

public class AustraliaMultipleStarter {

	public static void main(String[] args) {
		
		ArrayList<Problem> problems = new ArrayList<Problem>();
		
		for(int i=1; i<=71; i++){
			if(i!=26&&i!=47){	// problems with these files
				problems.add(ProblemHolmberg.readProblem("problem/p" + i));
			}
		}
		
		while (true){
		
			Problem problem = problems.get((int)(Math.random()*problems.size()));

			GA ga = new GA(problem);
			
			Database.addIndivudual(ga.startAlgorithm(300, 6000));
			System.out.println("Ende von Problem " + problem.getInstanceName() );
			
		}
		
//		System.out.println("Ende");

	}


}
