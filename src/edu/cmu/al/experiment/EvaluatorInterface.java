package edu.cmu.al.experiment;

public interface EvaluatorInterface {

	public double computeAccuracy();

	public double computePrecision();

	public double computeRecall();

	public double computeFMeasure();
}
