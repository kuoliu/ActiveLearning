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

	public static void updatePredictTable(List<PredictResult> predictionValue) {
		String updateSql = "update "
				+ Configuration.getPredictTable()
				+ " set confidence = ?, predict_result= ?  where product_id= ? ";
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
