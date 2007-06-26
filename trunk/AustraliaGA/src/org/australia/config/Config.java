package org.australia.config;

public class Config {
	
	private static boolean writeToDatabase = true;
	
	private static double percentageGreedy = 0.5;
	
	private static double fee = 0.5;
	
	
	
	public static final String MYSQL_HOST = "jegga.de";
	public static final String MYSQL_DATABASE = "australia";
	public static final String MYSQL_USER = "australia";
	public static final String MYSQL_PASS = "123456";

	
	
	
	
	
	public static double getPercentageGreedy() {
		return percentageGreedy;
	}

	public static void setPercentageGreedy(double percentageGreedy) {
		Config.percentageGreedy = percentageGreedy;
	}

	public static double getFee() {
		return fee;
	}

	public static void setFee(double fee) {
		Config.fee = fee;
	}

	public static boolean getWriteToDatabase() {
		return writeToDatabase;
	}

	public static void setWriteToDatabase(boolean writeToDatabase) {
		Config.writeToDatabase = writeToDatabase;
	}
	
	
	
	

}
