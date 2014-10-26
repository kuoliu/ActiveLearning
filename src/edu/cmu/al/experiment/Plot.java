package edu.cmu.al.experiment;

import MatlabPlot.MatlabPlot;

import com.mathworks.toolbox.javabuilder.*;

public class Plot implements PlotInterface {

	@Override
	public void barPlot() {
		try {
      MatlabPlot plot = new MatlabPlot();
			plot.barPlot();
		} catch (MWException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void linePlot() {
		try {
			MatlabPlot plot = new MatlabPlot();
			plot.linePlot();
		} catch (MWException e) {
			e.printStackTrace();
		}
	}
}