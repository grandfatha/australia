package org.australia.tests;

import org.australia.commons.Individual;
import org.australia.core.Population;

public class TestFitness {
	public static void main(String[] args) {
		Population a = new Population();
		a.initialize(100);
		Individual randomIndividual = a.getRandomIndividual();
		
		randomIndividual.calculateFitnessTest();
		System .out.println(randomIndividual.toString());
		for(int i =0; i<randomIndividual.getGene().length;i++){
			System.out.println(randomIndividual.getGene()[i]);
		}
		
		a.getProblem().getCap();
		double[] c = new double[a.getProblem().getCap().length];
		for(int i =0; i<c.length; i++){
			c[i]=0;
		}
		for(int i =0; i<a.getProblem().getCustomers(); i++){
			c[randomIndividual.getGene()[i]] += a.getProblem().getNeeds()[i];
		}
		for(int i =0; i<c.length; i++){
//			System.out.println("Warehouse " + i + ": " + c[i] + " Max:" + a.getProblem().getCap()[i]);
		}
		for(int i =0; i<a.getProblem().getFixcosts().length; i++){
			System.out.println( (int)a.getProblem().getFixcosts()[i]);
		}

	}

}
