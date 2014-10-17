package edu.cmu.al.util;

import java.io.*;

/**
 * Description: pack file manipulation
 * 
 * @author Kuo Liu
 */
public class FileManipulation {

	private FileInputStream fis = null;
	private InputStreamReader isr = null;
	private BufferedReader br = null;

	public FileManipulation() {
	}

	/**
	 * Get the corresponding buffered reader of a file
	 */
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

	/**
	 * Close the resource associated with one FileManipulation instance
	 */
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