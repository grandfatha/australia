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

public class ProblemHolmberg implements Problem {
	private double warehouses;
	private double customers;
	private double[] needs;
	private double[] cap;
	private double[] fixcosts;	// Eröffnungskosten für warehouse
	private double[][] costs;   // transportkosten
	


	public double[] getCap() {
		return cap;
	}


	public double[][] getCosts() {
		return costs;
	}


	public double getCustomers() {
		return customers;
	}


	public double[] getFixcosts() {
		return fixcosts;
	}


	public double[] getNeeds() {
		return needs;
	}


	public double getWarehouses() {
		return warehouses;
	}


	public static ProblemHolmberg readProblem(String s){
		File f = new File(s);
		return readProblem(f);
	}
	

	/**
	 * Factory to generate a new instance of problem from a given file
	 * 
	 * some code was adapted from Ulrich Sperlich
	 * 
	 * @author jochen
	 * @param f
	 * @return new Problem instance
	 */
	public static ProblemHolmberg readProblem(File f){
	ProblemHolmberg result = new ProblemHolmberg();
	
	
	// adapted from Ulrich Sperlich
    StreamTokenizer st;
    int zaehler = 0;
    int zaehler1 = 0;
    int zaehler2 = 0;
    int zaehler3 = 0;
    double help = 0;
    double help1 = 0;
    int needs1=0;
	int needs2=0;

	try {
		st = new StreamTokenizer(new FileReader(f));                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     

		st.slashStarComments( true );
		st.ordinaryChar( '/' );
		st.parseNumbers();
		st.eolIsSignificant( true );

		for ( int tval; (tval = st.nextToken()) != StreamTokenizer.TT_EOF; ) {
			if ( tval == StreamTokenizer.TT_NUMBER ){
    	  
	    	  if(zaehler==0){
	    		  //Anzahl der Lager wird gespeichert
	    		  result.warehouses=st.nval;
	    	  }

	    	  else if(zaehler==1){
	    		  //Anzahl der Kunden wird gespeichert
	    		  result.customers=st.nval;
	    		  
	    		  result.fixcosts=new double[(int)result.warehouses];
	    		  result.cap=new double[(int)result.warehouses];
//	    		  result.costs=new double[(int)result.customers+2][(int)result.warehouses+2];	// why +2????
	    		  result.costs=new double[(int)result.customers][(int)result.warehouses];
	    		  result.needs=new double[(int)result.customers];
//		    		  all=new double[(int)warehouses][(int)customers];
    	  }
	    	  
    	  else if(zaehler > 1 && zaehler < result.warehouses *2+2){
    		  
    		  if(zaehler%2==0){
    			//Fixkosten der Lager werden gespeichert
    			//Holmberg hat keine Deklaration
    			//an welcher Stelle die Fixkosten
    			//angegeben werden, hier wird von
    			//der ersten Stelle ausgegangen
    			  help=st.nval;
    			  result.fixcosts[zaehler1]=help;
 
    			  //@jochen wir denken es ist anders herum - oder auch nicht...
//    			  result.cap[zaehler1]=st.nval;
    			  
    			  zaehler1++;
    			  
    		  }
    		  else {
    			  //Kapazitäten der Lager werden gespeichert
    			  result.cap[zaehler2]=st.nval;
    			  
//    			  result.fixcosts[zaehler2]=st.nval;
    			  
    			  zaehler2++;
    			  
    		  }
    		}

    	  else if(zaehler >=result.warehouses*2+2 && zaehler < result.warehouses*2+2+result.customers ){
    		  
    		  //Bedürfnisse der Kunden werden gespeichert
    		  result.needs[zaehler3]=st.nval;
    		  
    		  zaehler3++;
    	  }
    	  
    	  
    	  else if(zaehler >= result.warehouses*2+2+result.customers){
    		  if(help1<result.warehouses){
    			 //Transportkosten werden gespeichert
    			  
    			  result.costs[needs1][needs2]=st.nval;
    			  
    			  needs2++;
    			  help1++;
    		  }
    		  else{
    			  help1=0;
    			  needs2=0;
    			  needs1++;
    			  result.costs[needs1][needs2]=st.nval;
    			  needs2++;
    			  help1++;
    		  }
    	  }
	        
	        zaehler++;
	        
	      }else if ( tval == StreamTokenizer.TT_EOL ){
	        
	      }
		}
	    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		return "Warehouses: " + warehouses + ", Customers: " + customers;
	}
	

}
