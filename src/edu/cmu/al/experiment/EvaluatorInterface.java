package edu.cmu.al.experiment;

public interface EvaluatorInterface {

	/**
	 * Compute the precision of the predicted result. It will compare the
	 * predicted result with the real document label. The precision is the
	 * number of true positive documents divide by the number of true positive
	 * documents plus number of false positive documents
	 * 
	 * @return precision
	 */
	public double computePrecision();

	/**
	 * Compute the recall of the predicted result. It will compare the predicted
	 * result with the real document label. The recall is the number of true
	 * positive documents divided by all positive documents that predicted
	 * 
	 * @return recall
	 */
	public double computeRecall();

	/**
	 * Compute the accuracy of the predicted result. It will compare the
	 * predicted result with the real document label. The accuracy is the number
	 * of true positive documents plus the number of true negative documents
	 * divided by all documents that predicted.
	 * 
	 * @return accuracy
	 */
	public double computeAccuracy();

	/**
	 * Compute the fMeasure of the predicted result. It will compare the
	 * predicted result with the real document label. The fMeasure is the denote
	 * the relationship between precision and recall. Here we consider the a
	 * parameter to be 0.5, which means assign equal weight to precision and
	 * recall.
	 * 
	 * @return fMeasure
	 */
	public double computeFMeasure();
}
