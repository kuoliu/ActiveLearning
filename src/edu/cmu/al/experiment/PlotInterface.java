package edu.cmu.al.experiment;

public interface PlotInterface {

  /**
   * Plot the experiment result as a point chart. The first argument is the number of instances that
   * labeled in each round, and the second argument is the total round of training. So for each
   * figure, the x-axis is the round number and the y-axis is the accuracy in that round.
   * 
   * @param number
   *          number of instances that labeled
   * @param round
   *          round that to be run
   * @return
   */
  public String dotPlot(int number, int round);
  public void barPlot();

  /**
   * Plot the experiment result as a line chart. The first argument is the number of instances that
   * labeled in each round, and the second argument is the total round of training. So for each
   * figure, the x-axis is the round number and the y-axis is the accuracy in that round.
   * 
   * @param number
   *          number of instances that labeled
   * @param round
   *          round that to be run
   * @return
   */
  public String linePlot(int number, int round);
  public void linePlot();
}