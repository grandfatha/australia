package org.australia.problem;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;

import org.australia.algorithm.Individual;
import org.australia.algorithm.IndividualImpl;

/**
 * @author jochen
 * Problem represents a "australia" problem
 * 
 *
 */

public class ProblemHolmberg extends ProblemHB{

	public static ProblemHolmberg readProblem(String s){
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
	public static ProblemHolmberg readProblem(File f){
	result = new ProblemHolmberg();
		
	result.instanceName = f.getName();
	
	// adapted from Ulrich Sperlich
    StreamTokenizer st;
	int iteration = 0;
	int facility = 0;
	int needs = 0;
	int customer = 0;
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
					if (iteration < ( result.warehouses*2+2) ){
						if(iteration%2 == 0){

							result.cap[facility] = st.nval;

//							result.fixcosts[facility] = st.nval;
							break;
						}else{
//							result.cap[facility] = st.nval;

							result.fixcosts[facility] = st.nval;

							facility++;
							break;
						}						
					}else if (iteration < (( result.warehouses*2+2) + result.customers) ){
						result.needs[needs] = st.nval;
						needs++;
						facility = 0;
						customer = 0;
						break;
					}else{
						if (customer == result.customers){
							facility ++;
							customer = 0;
						}
						 result.costs[customer][facility] = st.nval;
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
		
		return (ProblemHolmberg)result;
	}
}
