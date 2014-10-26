package edu.cmu.al.experiment;

import edu.cmu.al.ml.Classifier;
import edu.cmu.al.sampling.BasicSampling;
import edu.cmu.al.simulation.LabelingSimulation;

public interface Experiment {

  public void doExperiment();

  public void doExperiment(BasicSampling sampling, Classifier classifier,
          LabelingSimulation labeling);

  public void doExperiment(int i, BasicSampling sampling, Classifier classifier,
          LabelingSimulation labeling);

  public void testSampling(int i, Classifier classifier);

  public void testModel(Classifier classifier);

  public void plotResult();

  public void plotResult(String outputFileName, String title, String... files);

  public void storeInFile(String outputFileName, double[] cost, double[] accuracy);

  public void storeInFile();

  public double[] getTestSamplingAccuracies();

  public double[] getTestModelAccuracies();

  public double[] getAccuracies();
}
