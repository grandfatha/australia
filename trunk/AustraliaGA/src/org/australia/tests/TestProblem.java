package org.australia.tests;

import java.io.File;

import org.australia.problem.Problem;
import org.australia.problem.ProblemBoccia;

public class TestProblem {
	public static void main(String[] args) {
		File f= new File("i5050_2.plc");
		Problem p = ProblemBoccia.readProblem(f);
		System.out.println(p.toString());
		
		System.out.println("Cap:");
		for(int i=0; i < p.getCap().length; i++){
			System.out.print((int)p.getCap()[i] +",");
		}

		System.out.println("Needs:");
		System.out.println();
		for(int i=0; i < p.getNeeds().length; i++){
			System.out.println((int)p.getNeeds()[i] +",");
		}
	}
}
