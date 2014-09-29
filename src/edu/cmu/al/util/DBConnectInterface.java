package edu.cmu.al.util;

import java.util.List;

import edu.cmu.al.instance.Instance;

/**
 * @author Shuo Zheng, Zhengxiong Zhang
 * 
 *         The DBConnectInterface interface can connect to the database. Then, it can read data from
 *         database and store data into the database. Out interface can also update the existed data
 *         in the database.
 * 
 */
public interface DBConnectInterface {
  /**
   * The connectDB() method can connect to the database and get the connection parameter in the
   * class.
   */
  public void connectDB();

  /**
   * The closeDB() method can close the database connection.
   */
  public void closeDB();

  /**
   * The getData() method can read instance data from the database.
   * 
   * @return instance list data, read from the database.
   * 
   */
  public List<Instance> getData();

  /**
   * The storeData() method can build a new table and store the data into the table.
   * 
   * @param result
   *          Store the result data into the database.
   * 
   */
  public void storeData(String tableName, List<String> columns, List<Instance> result);

  /**
   * The updateData() method can update the data in the table.
   * 
   * @param data
   *          Update the existing data in the table.
   * 
   */
  public void updateData(String tableName, List<Instance> data);

}
