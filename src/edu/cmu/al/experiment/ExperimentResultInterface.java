package edu.cmu.al.experiment;

import java.util.List;

import edu.cmu.al.instance.Instance;

/**
 * This interface is used to generate the experiment result. Such as the accuracy, recall, P@N
 * precision. Also, in order to test whether the low precision problem is due to training model or
 * feature engineering, it will label all the instances to check this problem.
 * 
 */
public interface ExperimentResultInterface {

  /**
   * Compute the accuracy of the predicted result. It will compare the predicted result with the
   * real document label. The accuracy is the number of true positive documents plus the number of
   * true negative documents divided by all documents that predicted.
   * 
   * @param pred
   *          the predicted result
   * @param real
   *          the real result
   * @return accuracy
   */
  public float getAccuracy(List<Instance> pred, List<Instance> real);

  /**
   * Compute the recall of the predicted result. It will compare the predicted result with the real
   * document label. The recall is the number of true positive documents divided by all positive
   * documents that predicted
   * 
   * @param pred
   *          the predicted result
   * @param real
   *          the real result
   * @return recall
   */
  public float getRecall(List<Instance> pred, List<Instance> real);

  /**
   * Compute the P@K precision of the predicted result. It will compare the predicted result with
   * the real document label. The P@K is defined as the precision of first K predicted document.
   * 
   * @param pred
   *          the predicted result
   * @param real
   *          the real result
   * @param k
   *          first k document
   * @return precision
   */
  public float computePAtK(List<Instance> pred, List<Instance> real, int k);

  /**
   * Label all the instances. In this function, it will get the real labels of the instances from
   * the database and then assign the real labels for the given instances.
   * 
   * @param list
   *          the instances to be labeled
   * @return
   */
  public List<Instance> labelAllInstance(List<Instance> list);
}