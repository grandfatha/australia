package org.australia.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import org.australia.algorithm.Individual;
import org.australia.algorithm.IndividualImpl;
import org.australia.config.Config;
import org.australia.problem.Problem;
import org.australia.problem.ProblemHolmberg;


public class Database {
	
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
		 	
		 	statement.execute( "INSERT INTO individuals (gene, fitness, valid, instance) "+
		 			"VALUES ('"+ individual.getGeneString() +"', " + individual.getFitness() +", " + valid + ", '" + individual.getProblem().getInstanceName() +"')");
		
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
	
	
	public static Collection<Individual> getIndividualsFromDatabase(Problem problem){
		Collection<Individual> result = new ArrayList<Individual>();
		
		Connection connection = getConnection();
		
		if(connection==null){
			return null;
		}

		System.out.println("Connection established");
		
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT gene FROM individuals WHERE instance = '" + problem.getInstanceName() +"'");
			
			System.out.println("SELECT gene FROM individuals WHERE instance = '" + problem.getInstanceName() +"'");

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
			Config.setWriteToDatabase(false);
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
