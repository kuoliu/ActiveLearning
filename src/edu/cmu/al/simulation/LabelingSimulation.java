package edu.cmu.al.simulation;

import java.util.List;
import java.util.Set;

import edu.cmu.al.instance.Instance;

/**
 * @author Shuo Zheng, Zhengxiong Zhang
 * 
 *         The LabelingInterface interface is used to select instances from the prediction result,
 *         label a certain number of instances and send back the result. The interface can simulate
 *         the user's behavior, and label instances.
 */
public interface LabelingSimulation {

  public void label(Set<String> productIds);
  
}
