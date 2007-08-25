/*
 * HolmbergOptimal.java
 * 
 * Created on 26.06.2007, 23:47:02
 * 
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.australia.util;

import java.util.Map;
import java.util.TreeMap;

import org.australia.algorithm.Individual;
import org.australia.problem.Problem;
import org.australia.problem.ProblemHolmberg;

/**
 *
 * @author Daniel_h4x
 */
public class HolmbergOptimal {
	
	private static Map<String, Double> optimals = new TreeMap<String, Double>();
	
	private static Map<String, Double> getOptimals(){
		
		if(optimals.size() < 1){
		

		    optimals.put("p1", 8848.0);
		    optimals.put("p2", 7913.0);
		    optimals.put("p3", 9314.0);
		    optimals.put("p4", 10714.0);
		    optimals.put("p5", 8838.0);
		    optimals.put("p6", 7777.0);
		    optimals.put("p7", 9488.0);
		    optimals.put("p8", 11088.0);
		    optimals.put("p9", 8462.0);
		    
		    optimals.put("p10", 7617.0);
		    optimals.put("p11", 8932.0);
		    optimals.put("p12", 10132.0);
		    optimals.put("p13", 8252.0);
		    optimals.put("p14", 7137.0);
		    optimals.put("p15", 8808.0);
		    optimals.put("p16", 10408.0);
		    optimals.put("p17", 8227.0);
		    optimals.put("p18", 7125.0);
		    optimals.put("p19", 8886.0);
		    
		    optimals.put("p20", 10486.0);
		    optimals.put("p21", 8068.0);
		    optimals.put("p22", 7092.0);
		    optimals.put("p23", 8746.0);
		    optimals.put("p24", 10273.0);
		    optimals.put("p25", 11630.0);
		    optimals.put("p26", 10771.0);
		    optimals.put("p27", 12322.0);
		    optimals.put("p28", 13722.0);
		    optimals.put("p29", 12371.0);
		    
		    optimals.put("p30", 11331.0);
		    optimals.put("p31", 13331.0);
		    optimals.put("p32", 15331.0);
		    optimals.put("p33", 11629.0);
		    optimals.put("p34", 10632.0);
		    optimals.put("p35", 12232.0);
		    optimals.put("p36", 13832.0);
		    optimals.put("p37", 11258.0);
		    optimals.put("p38", 10551.0);
		    optimals.put("p39", 11824.0);
		    
		    optimals.put("p40", 13024.0);
		    optimals.put("p41", 6589.0);
		    optimals.put("p42", 5663.0);
		    optimals.put("p43", 5214.0);
		    optimals.put("p44", 7028.0);
		    optimals.put("p45", 6251.0);
		    optimals.put("p46", 5651.0);
		    optimals.put("p47", 6228.0);
		    optimals.put("p48", 5596.0);
		    optimals.put("p49", 5302.0);
		    
		    optimals.put("p50", 8741.0);
		    optimals.put("p51", 7414.0);
		    optimals.put("p52", 9178.0);
		    optimals.put("p53", 8531.0);
		    optimals.put("p54", 8777.0);
		    optimals.put("p55", 7654.0);
		    optimals.put("p56", 21103.0);
		    optimals.put("p57", 26039.0);
		    optimals.put("p58", 37239.0);
		    optimals.put("p59", 27282.0);
		    
		    optimals.put("p60", 20534.0);
		    optimals.put("p61", 24454.0);
		    optimals.put("p62", 32643.0);
		    optimals.put("p63", 25105.0);
		    optimals.put("p64", 20530.0);
		    optimals.put("p65", 24445.0);
		    optimals.put("p66", 31415.0);
		    optimals.put("p67", 24848.0);
		    optimals.put("p68", 20538.0);
		    optimals.put("p69", 24532.0);
		    
		    optimals.put("p70", 32321.0);
		    optimals.put("p71", 25540.0);
    
		}
    
	return optimals;
	}

    
    
    public static double getOptimal(Problem problem){
    	return getOptimals().get(problem.getInstanceName().toLowerCase());
    }
    
    public static boolean isOptimal(Individual individual){    	
    	return getOptimal(individual.getProblem()) == (double)individual.getFitness();
    }
    
    

}
