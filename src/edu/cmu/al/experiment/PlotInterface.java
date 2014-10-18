package edu.cmu.al.experiment;

public interface PlotInterface {

	/**
	 * Plot the experiment result as a bar chart. The first argument is the
	 * number of instances that labeled in each round, and the second argument
	 * is the total round of training. So for each figure, the x-axis is the
	 * round number and the y-axis is the accuracy, precision, recall, fMeasure
	 * in that round.
	 * 
	 * @return
	 */
	public void barPlot();

	/**
	 * Plot the experiment result as a line chart. The first argument is the
	 * number of instances that labeled in each round, and the second argument
	 * is the total round of training. So for each figure, the x-axis is the
	 * round number and the y-axis is the accuracy, precision, recall, fMeasure
	 * in that round.
	 * 
	 * 
	 * @return
	 */
	public void linePlot();

}

