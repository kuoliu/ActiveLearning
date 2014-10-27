package edu.cmu.al.experiment;

import edu.cmu.al.ml.Classifier;
import edu.cmu.al.sampling.BasicSampling;
import edu.cmu.al.simulation.LabelingSimulation;

public interface Experiment {

  public void doExperiment(BasicSampling sampling, Classifier classifier,
          LabelingSimulation labeling, String outputFileName);

  public void plotResult(String outputFileName, String title, String... files);
}
