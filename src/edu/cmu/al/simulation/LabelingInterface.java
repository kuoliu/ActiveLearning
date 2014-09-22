package edu.cmu.al.simulation;

import java.util.List;

import edu.cmu.al.instance.Instance;

/**
 * @author Shuo Zheng, Zhengxiong Zhang
 * 
 *         The LabelingInterface interface is used to select instances from the prediction result,
 *         label a certain number of instances and send back the result. The interface can simulate
 *         the user's behavior, and label instances.
 */
public interface LabelingInterface {

  /**
   * The selectKInstance() method can randomly select k instances from the prediction result.
   * 
   * @param list
   *          The prediction result of the model.
   * @param k
   *          The number of selected instances.
   * @return the selected instances list.
   * 
   */
  public List<Instance> selectKInstance(List<Instance> list, int k);

  /**
   * The labelKInstance() method can label k instances from the selected instances, and return back
   * the list of the labeled instances.
   * 
   * @param list
   *          The selected instances from the prediction result.
   * @param k
   *          The number of selected instances.
   * @return the labeled instances list.
   * 
   */
  public List<Instance> labelKInstance(List<Instance> list, int k);

  /**
   * The sendBackInstance() method can send the labeled instances back to the model.
   * 
   * @param list
   *          The list of instances to be returned to the model.
   * @return the list of returned instances.
   * 
   */
  public List<Instance> sendBackInstance(List<Instance> list);
}
