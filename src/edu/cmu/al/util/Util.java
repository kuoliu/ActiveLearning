package edu.cmu.al.util;

import java.io.BufferedReader;
import java.util.HashSet;
import java.util.List;

import edu.cmu.al.ml.PredictResult;

/**
 * Description: Some commonly used function across the project can be written
 * here
 * 
 * @author Kuo Liu
 */
public class Util {
  
  public static final String DIR = "output/";

  public static final String accuracy = "accuracy.txt";

  public static final String testSamplingAccuracy = "testSamplingAccuracy.txt";

  public static final String testModelAccuracy = "testModelAccuracy.txt";

	public static void updatePredictTable(List<PredictResult> predictionValue, String confidenceCol) {
		String updateSql = "update "
				+ Configuration.getPredictTable()
				+ " set " + confidenceCol + " = ?, predict_result= ?  where product_id= ? ";
		System.out.println(updateSql);
		try {
			for (int i = 0; i < predictionValue.size(); i++) {
				SqlManipulation.update(updateSql, 
				    predictionValue.get(i).getPrediction(), 
				    predictionValue.get(i).getPredictLable(),
				    predictionValue.get(i).getProductId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static HashSet<String> loadFileToHashSet(String file) {
		HashSet<String> set = new HashSet<String>();
		FileManipulation fileManip = new FileManipulation();
		BufferedReader br = fileManip.getBufferedReader(file,
				Configuration.getFileFormat());
		String buffer = "";
		try {
			while ((buffer = br.readLine()) != null) {
				buffer = buffer.trim();
				set.add(buffer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		fileManip.closeFile();
		return set;
	}
}
