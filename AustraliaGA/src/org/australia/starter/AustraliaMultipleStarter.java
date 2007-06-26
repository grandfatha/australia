package org.australia.starter;

import java.util.ArrayList;
import java.util.Collection;

import org.australia.algorithm.GA;
import org.australia.algorithm.Individual;
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
		
		for (Problem problem : problems) {
			GA ga = new GA(problem);
			
			for(int i=1; i<=5;i++){
				Database.addIndivudual(ga.startAlgorithm(200, 3000));
				System.out.println("Ende Durchgang " + i + " von Problem " + problem.getInstanceName() );
			}
		}
		
		
		System.out.println("Ende");

	}


}
