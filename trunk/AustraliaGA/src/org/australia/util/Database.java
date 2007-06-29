package org.australia.util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.australia.algorithm.Individual;
import org.australia.algorithm.IndividualImpl;
import org.australia.config.Config;
import org.australia.problem.Problem;
import org.australia.problem.ProblemHolmberg;


public class Database {
	
	
	public static void addIndivudualBenchmark(Individual individual, String benchmark, double duration , double durationForBest, int iterations, int populationSize){
		
		// valid = 0; invalid = -1
		int valid = -1;
		if(individual.isValid()){
			valid=0;
		}

		String query = "INSERT INTO individuals "+
			"(gene, fitness, valid, instance, fee, duration, durationForBest, benchmark, populationSize, iterations, selectionMethod, dateAdded) "+
			"VALUES (" +
			"'"+ individual.getGeneString() + "', " + // gene
			individual.getFitness() +", " + 	// fitness
			valid + ", " + 						// valid
			"'" + individual.getProblem().getInstanceName() +"', " + // instance
			Config.getFee() + ", " +			// fee
			duration + ", " +
			durationForBest  + ", " +
			"'" + benchmark  + "', " +
			populationSize  + ", " +
			iterations  + ", " +
			Config.getSelectionMethod()  + ", " +
			"NOW()" + 
			")";
		
		System.out.println(query);

		insert(query);
		
	}
	
	public static void insert(String query){
		
		Connection connection = getConnection();
		
		if(connection==null){
			return;
		}
		
		try {
		 	Statement statement = connection.createStatement();
		 	
		 	statement.execute(query);
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				connection.close();
				System.out.println("Insert successful");
			} catch (SQLException e) {
				System.out.println("Could not close connection to database.");
			}
		}
	}
	

	
	public static void addIndivudual(Individual individual){
		
		Connection connection = getConnection();
		
		if(connection==null){
			return;
		}
		
		// valid = 0; invalid = -1
		int valid = -1;
		if(individual.isValid()){
			valid=0;
		}
		
		try {
		 	Statement statement = connection.createStatement();
		 	
		 	statement.execute( "INSERT INTO individuals (gene, fitness, valid, instance, fee) "+
		 			"VALUES ('"+ individual.getGeneString() +"', " + individual.getFitness() +", " + valid + ", '" + individual.getProblem().getInstanceName() +"', "+ Config.getFee() +")");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				connection.close();
				System.out.println("Individual successfully added to database");
			} catch (SQLException e) {
				System.out.println("Could not close connection to database.");
			}
		}
	}
	
	public static Individual getBestValidIndividualFromDatabase(Problem problem){
		Iterator<Individual> it = getIndividualsFromDatabase(problem, true, 1).iterator();
		if(it.hasNext()){
			return it.next();
		}else{
			return null;
		}
	}

	
	public static Collection<Individual> getIndividualsFromDatabase(Problem problem){
		return 	getIndividualsFromDatabase(problem, false);
	}
		
	public static Collection<Individual> getIndividualsFromDatabase(Problem problem, boolean onlyValid){
		return getIndividualsFromDatabase(problem, onlyValid, 0);
	}

	public static Collection<Individual> getIndividualsFromDatabase(Problem problem, boolean onlyValid, int limit){
		Collection<Individual> result = new ArrayList<Individual>();
		
		Connection connection = getConnection();
		
		if(connection==null){
			return null;
		}

		System.out.println("Connection established");
		
		try {
			Statement statement = connection.createStatement();
			
			String query = "SELECT gene FROM individuals WHERE "+ (onlyValid ? "valid=0 AND " : "" )  +"instance = '" + problem.getInstanceName() +"' " + (limit >0 ? " ORDER BY fitness ASC LIMIT 0, "+limit : "" );
			ResultSet resultSet = statement.executeQuery(query);
			
			System.out.println(query);

			while(resultSet.next()){
				String gene = resultSet.getString(1);
				Individual individual = new IndividualImpl(problem, Utils.getArray(gene));
				result.add(individual);
			}
			
			System.out.println("Found " + result.size() + " Individuals in Database");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}
	
	
	private static Connection getConnection(){
		Connection conn=null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Could not find drivers for MySql-Database -> check classpath");
			Config.setWriteToDatabase(false);
			return null;
		}
		
		try {
			// Set Timeout to 120 seconds
			DriverManager.setLoginTimeout(120);
			
			// try to g
		    conn = DriverManager.getConnection("jdbc:mysql://"+ Config.MYSQL_HOST + "/"+ Config.MYSQL_DATABASE, Config.MYSQL_USER, Config.MYSQL_PASS);

		} catch (SQLException ex) {
		    System.out.println("Could not establish connection to database -> check your network connection");
//			Config.setWriteToDatabase(false);
			return null;
		}
		
		return conn;
	}
	
	public static void main(String[] args) {

		Problem problem = ProblemHolmberg.readProblem("problem/p1");
		
		Collection<Individual> individualsFromDatabase = getIndividualsFromDatabase(problem);
		
		for (Individual individual : individualsFromDatabase) {
			System.out.println(individual);
		}
		System.out.println("Ende");
	}

}
