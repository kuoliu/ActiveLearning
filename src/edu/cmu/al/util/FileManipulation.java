package edu.cmu.al.util;

import java.io.*;

/**
 * Description: pack file manipulation
 */
public class FileManipulation {

	private FileInputStream fis = null;
	private InputStreamReader isr = null;
	private BufferedReader br = null;

	public FileManipulation() {
	}

	public BufferedReader getBufferedReader(String file, String format) {
		try {
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis, format);
			br = new BufferedReader(isr);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return br;
	}

	public void closeFile() {
		try {
			if (br != null)
				br.close();
			if (isr != null)
				isr.close();
			if (fis != null)
				fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}