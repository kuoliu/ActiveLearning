package edu.cmu.al.ml;

public class ProductIDPredictionPair {
	String productId;
	double prediction;
	
	public ProductIDPredictionPair(String s, double d) {
		this.productId = s;
		this.prediction = d;
	}
	
	public String getProductId() {
		return this.productId;
	}
	
	public double getPrediction() {
		return this.prediction;
	}
}
