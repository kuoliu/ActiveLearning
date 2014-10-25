package edu.cmu.al.experiment;

import java.io.IOException;

public class PyPlot implements Plot {

  @Override
  public void barPlot() {
    return;
  }

  @Override
  public void linePlot() {
    return;
  }

  // python python/PylabPlotTool.py test OnlyTest accuracy.txt accuracy precision.txt precision
  @Override
  public void linePlot(String outputFileName, String title, String... files) {
    if (files == null || files.length == 0 || files.length % 2 == 1) {
      System.out.println("PyPlot error. The input is wrong...");
      return;
    }
    
    StringBuffer sb = new StringBuffer();
    sb.append("python python/PylabPlotTool.py ").append(outputFileName).append(" ").append(title);
    for (int i = 0; i < files.length; i++) {
      sb.append(" ").append(files[i]);
    }
    // System.out.println(sb.toString());
    
    try {
      Runtime.getRuntime().exec(sb.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
}
