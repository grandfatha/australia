package org.australia.config;

public class Config {
	
	
	private static double percentageGreedy = 0.3;
	private static double oddsMutation = 0.7;
	
	private static double fee = 1;
	private static double newGenerationSize = 2.0;
	
	
	private static boolean writeToDatabase = true;
	
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

	public static double getNewGenerationSize() {
		return newGenerationSize;
	}

	public static void setNewGenerationSize(double newGenerationSize) {
		Config.newGenerationSize = newGenerationSize;
	}

	public static double getOddsMutation() {
		return oddsMutation;
	}

	public static void setOddsMutation(double oddsMutation) {
		Config.oddsMutation = oddsMutation;
	}
	
	
	
	

}
