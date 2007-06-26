package org.australia.tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.australia.util.Database;

public class MySqlTest {
	public static void main(String[] args) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
		    Connection conn = 
		       DriverManager.getConnection("jdbc:mysql://localhost/australia?" + 
		                                   "user=australia&password=123456");
		    	

		    // Do something with the Connection
		    
		    conn.close();

		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}

	}
}
