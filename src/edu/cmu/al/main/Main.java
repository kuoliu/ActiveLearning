package edu.cmu.al.main;

import java.io.IOException;

import edu.cmu.al.experiment.ExperimentResult;
import edu.cmu.al.feature.BasicFeatureExtractor;
import edu.cmu.al.feature.FeatureExtractor;

/**
 * The main class to run main function
 * 
 * @author Kuo Liu
 */
public class Main {
	public static int round = 100;
	public static void main(String[] args) {
		
		Preprocess.run();
		
		int featureId = 1;
		FeatureExtractor featureExtractor = new BasicFeatureExtractor();
		featureId = featureExtractor.extractFeature(featureId);
//		ExperimentResult experiment = new ExperimentResult(round);
//		experiment.doExperiment();
//		System.out.println(System.getProperty("user.dir"));
//		String[] cmd=new String[2];
//		cmd[0] ="cmd.exe";
//	    cmd[1]="matlab";
//	   
//		try {
//			Runtime.getRuntime().exec(cmd);
//			Runtime.getRuntime().exec("cmd /c cmd.exe /c mkdir cc" );
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			System.out.println("a");
//			e.printStackTrace();
//		} 
	}
}
