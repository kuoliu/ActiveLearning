package edu.cmu.al.util;

import java.io.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class testFileManipulation {

	@Test
	public void test() {
		FileManipulation fileManip = new FileManipulation();
		BufferedReader br = fileManip.getBufferedReader(Configuration.getTmpPath(), Configuration.getFileFormat());
		// Then read something
		fileManip.closeFile();
	}

}
