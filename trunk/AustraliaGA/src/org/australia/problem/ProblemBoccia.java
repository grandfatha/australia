package org.australia.problem;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;

/**
 * @author jochen
 * Problem represents a "australia" problem
 * 
 *
 */

public class ProblemBoccia extends ProblemHB{
	
	public static ProblemBoccia readProblem(String s){
		File f = new File(s);
		return readProblem(f);
	}
	
	/**
	 * Factory to generate a new instance of problem from a given file
	 * 
	 * some code was adapted from Ulrich Sperlich
	 * 
	 * @author benjamin
	 * @param f
	 * @return new Problem instance
	 */
	public static ProblemBoccia readProblem(File f){
		result = new ProblemBoccia();
		result.instanceName = f.getName();
		
		// adapted from Ulrich Sperlich
		StreamTokenizer st;
		int iteration = 0;
		int facility = 0;
		int facilityCap = 0;
		int facilityCost = 0;
		int needs = 0;
		int customer = 0;
		
		//ab hier beginnt das einlesen
		
		try {
			st = new StreamTokenizer(new FileReader(f));                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
		
			st.slashStarComments( true );
			st.ordinaryChar( '/' );
			st.parseNumbers();
			st.eolIsSignificant( true );
			
			for ( int tval; (tval = st.nextToken()) != StreamTokenizer.TT_EOF; ) {
				if ( tval == StreamTokenizer.TT_NUMBER ){
					switch (iteration) {
					case 0:
						result.warehouses = st.nval;
						break;
						
					case 1:
			    		  result.customers=st.nval;
			    		  result.fixcosts=new double[(int)result.warehouses];
			    		  result.cap=new double[(int)result.warehouses];
			    		  result.costs=new double[(int)result.customers][(int)result.warehouses];
			    		  result.needs=new double[(int)result.customers];
			    		  break;
						
					default:
						if (iteration < ( result.customers + 2)){
							result.needs[needs] = st.nval;
							needs++;
							break;
							
						}else if (iteration < (( result.customers+ 2) + result.warehouses)){
							result.cap[facilityCap] = st.nval;
							facilityCap++;
							break;
							
						}else if (iteration < (( result.customers + 2) + result.warehouses * 2)){
							result.fixcosts[facilityCost] = st.nval;
							facilityCost++;
							break;
						}else{
							if (customer == result.customers){
								facility ++;
								customer = 0;
							}
							 result.costs[customer][facility] = st.nval * result.getNeeds()[customer];
							 customer++;
							 break;
						}
					}
					iteration++;
				}	
			}			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return (ProblemBoccia) result;
	}
//	
//	/*  testing */
//	public static void main(String[] args) {
//		Problem problem = ProblemBoccia.readProblem("problem/i50100_1.plc");
//		System.out.println(problem);
//		
//		System.out.println("Demands:");
//		for (int i=0; i< problem.getNeeds().length; i++) {
//			
//			System.out.println(i + ": " +problem.getNeeds()[i]);
//		}
//		System.out.println("Capacity:");
//		for (int i=0; i< problem.getCap().length; i++) {
//			
//			System.out.println(i + ": " +problem.getCap()[i]);
//		}
//
//	}
	

}
