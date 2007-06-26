package org.australia.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.australia.algorithm.Individual;
import org.australia.config.Config;


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
		    conn = DriverManager.getConnection("jdbc:mysql://"+ Config.MYSQL_HOST + "/"+ Config.MYSQL_DATABASE, Config.MYSQL_USER, Config.MYSQL_PASS);

		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		}
		
		return conn;
	}

}
