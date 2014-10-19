package edu.cmu.al.ml;

public class PredictResult {
	String productId;
	double prediction;
	double predictLabel;
	
	public PredictResult(String s, double d, double label) {
		this.productId = s;
		this.prediction = d;
		this.predictLabel = label;
	}
	
	public String getProductId() {
		return this.productId;
	}
	
	public double getPrediction() {
		return this.prediction;
	}
	
	public double getPredictLable() {
		return this.predictLabel;
	}
}
