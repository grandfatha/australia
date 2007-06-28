package org.australia.config;

public class Config {
	
	private static int selectionMethod = 0;	// 0=random
											// 1= rouletteWheel

	private static double percentageGreedy = 0.3;
	private static double oddsMutation = 0.5;
	private static double percentageForeignIndividuals = 0.0;
	
	private static double fee = 20;
	private static double newGenerationSize = 2.0;
	
	
	private static boolean writeToDatabase = true;
	
	public static final String MYSQL_HOST = "jegga.de";
	public static final String MYSQL_DATABASE = "australia";
	public static final String MYSQL_USER = "australia";
	public static final String MYSQL_PASS = "123456";

	
	
	
	/***********************************************************************************/
	/* Getter and Setter ***************************************************************/
	
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

	public static double getPercentageForeignIndividuals() {
		return percentageForeignIndividuals;
	}

	public static void setPercentageForeignIndividuals(
			double percentageForeignIndividuals) {
		Config.percentageForeignIndividuals = percentageForeignIndividuals;
	}

	public static int getSelectionMethod() {
		return selectionMethod;
	}

	public static void setSelectionMethod(int selectionMethod) {
		Config.selectionMethod = selectionMethod;
	}
	
	
	
	

}
