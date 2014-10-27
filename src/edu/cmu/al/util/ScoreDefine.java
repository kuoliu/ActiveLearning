package edu.cmu.al.util;

/**
 * Description: Some general definition based on the score
 */
public class ScoreDefine {
	
	/**
	 * The threshold for pos/neg instances
	 */
	
	public static int posSocre = 3;      
	
	static int numberOfInstanceToLabel = 1000;
	
	public static int getNumberOfInstanceToLabel(double precision){
		if (precision < 0.1){
			return numberOfInstanceToLabel;
		}else if (precision < 0.2){
			return numberOfInstanceToLabel/2;
		}else if (precision < 0.3){
			return numberOfInstanceToLabel/3;
		}else if (precision < 0.4){
			return numberOfInstanceToLabel/4;
		}else if (precision < 0.5){
			return numberOfInstanceToLabel/5;
		}else if (precision < 0.6){
			return numberOfInstanceToLabel/6;
		}else if (precision < 0.7){
			return numberOfInstanceToLabel/7;
		}else if (precision < 0.8){
			return numberOfInstanceToLabel/8;
		}else if (precision < 0.9){
			return numberOfInstanceToLabel/9;
		}else {
			return numberOfInstanceToLabel/10;
		}
	}
}