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

public class ProblemBoccia implements Problem{
	private double warehouses;
	private double customers;
	private double[] needs;
	private double[] cap;
	private double[] fixcosts;	// Eröffnungskosten für warehouse
	private double[][] costs;   // transportkosten
	

	// Getter
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


	public static ProblemBoccia readProblem(String s){
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
	public static ProblemBoccia readProblem(File f){
		ProblemBoccia result = new ProblemBoccia();
	
		
		// adapted from Ulrich Sperlich
		StreamTokenizer st;
		int zaehler=0;
		int zaehler1=0;
		int zaehler2=0;
		int zaehler3=0;
		int help=0;
		int needs1=0;
		int needs2=0;
		int help1=0;
		
		
		//ab hier beginnt das einlesen
		
		try {
			st = new StreamTokenizer(new FileReader(f));                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
		
		st.slashStarComments( true );
		st.ordinaryChar( '/' );
		st.parseNumbers();
		st.eolIsSignificant( true );
		
		for ( int tval; (tval = st.nextToken()) != StreamTokenizer.TT_EOF; )
		{
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
				  result.costs=new double[(int)result.customers+2][(int)result.warehouses+2];
				  result.needs=new double[(int)result.customers];
//				  all=new double[(int)warehouses][(int)customers];
				  
				  
				 
				  
			  }
			  else if(zaehler > 1 && zaehler < result.customers+2){
				  //Bedürfnisse der Kunden werden gespeichert
				  result.needs[zaehler1]=st.nval;
				  zaehler1++;
				}
			  else if(zaehler >=result.customers+2 && zaehler < result.customers+2+result.warehouses ){
				  //Kapazitäten der Lager werden gespeichert
				  result.cap[zaehler3]=st.nval;
				  zaehler3++;
				  
				  
			  }
			  
			  
			  
			  else if(zaehler >= result.customers+2+result.warehouses &&zaehler<result.customers+2+result.warehouses*2){
				  //Fixkosten der Lager werden gespeichert
				  result.fixcosts[zaehler2]=st.nval;
				  zaehler2++;
			  }
			  else if(zaehler >= result.customers+2+result.warehouses*2){
				  //Transportkosten werden gespeichert
				  if(help==result.customers){
					  help=0;
					  help1++;
				  }
				  result.costs[help][help1]=st.nval;
				  result.costs[help][help1]=result.costs[help][help1]*result.needs[help];
				 
				  help++;	    	  }
			  
		    
		    zaehler++;
		    
		  }
		  else if ( tval == StreamTokenizer.TT_EOL ){
		    
		  }
		}
		
			
		//Ende Einlesen
	    
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
	
	/*  testing */
	public static void main(String[] args) {
		Problem problem = ProblemBoccia.readProblem("problem/i50100_1.plc");
		System.out.println(problem);
		
		System.out.println("Demands:");
		for (int i=0; i< problem.getNeeds().length; i++) {
			
			System.out.println(i + ": " +problem.getNeeds()[i]);
		}
		System.out.println("Capacity:");
		for (int i=0; i< problem.getCap().length; i++) {
			
			System.out.println(i + ": " +problem.getCap()[i]);
		}

	}
	

}
