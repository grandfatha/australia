package org.australia.tests;

import org.australia.algorithm.Individual;
import org.australia.algorithm.IndividualImpl;
import org.australia.algorithm.Population;
import org.australia.algorithm.PopulationFastTableImpl;
import org.australia.problem.Problem;
import org.australia.problem.ProblemHolmberg;

public class FastTableImplTest {
	public static void main(String[] args) {
		
		Problem problem = ProblemHolmberg.readProblem("problem/p1");
		Population p = new PopulationFastTableImpl(problem);
		
		
		p.add(IndividualImpl.generateRandomIndividual(problem));
		p.add(IndividualImpl.generateRandomIndividual(problem));
		p.add(IndividualImpl.generateRandomIndividual(problem));
		p.add(IndividualImpl.generateRandomIndividual(problem));
		p.add(IndividualImpl.generateRandomIndividual(problem));
		p.add(IndividualImpl.generateRandomIndividual(problem));
		p.add(IndividualImpl.generateRandomIndividual(problem));
		p.add(IndividualImpl.generateRandomIndividual(problem));
		p.add(IndividualImpl.generateRandomIndividual(problem));
		p.add(IndividualImpl.generateRandomIndividual(problem));
		p.add(IndividualImpl.generateGreedyIndividualWithRouletteWheel(problem));
		p.add(IndividualImpl.generateGreedyIndividualWithRouletteWheel(problem));
		p.add(IndividualImpl.generateGreedyIndividualWithRouletteWheel(problem));
		p.add(IndividualImpl.generateGreedyIndividualWithRouletteWheel(problem));

		System.out.println(p);
		
	}

}
