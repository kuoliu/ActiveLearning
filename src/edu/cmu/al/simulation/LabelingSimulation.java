package edu.cmu.al.simulation;

import java.util.Collection;
import java.util.List;

/**
 * @author Shuo Zheng, Zhengxiong Zhang
 * 
 *         The LabelingInterface interface is used to select instances from the prediction result,
 *         label a certain number of instances and send back the result. The interface can simulate
 *         the user's behavior, and label instances.
 */
public interface LabelingSimulation {

  public void labelProductId(Collection<String> productIds);
  
  public void firstLabel(int k);

  // public void labelAll();

  // public List<String> randomLabelByNum(int n);

  // public List<String> randomLabelByRatio(double ratio);

  public int getUnlabeledNumber();

  public int getAllNumber();
}
