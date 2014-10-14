package edu.cmu.al.util;

import java.io.*;

/**
 * Description: pack file manipulation
<<<<<<< HEAD
=======
 * 
 * @author Kuo Liu
>>>>>>> 9c8df1751a5e1c886fe2cf0bca30f577ae100058
 */
public class FileManipulation {

	private FileInputStream fis = null;
	private InputStreamReader isr = null;
	private BufferedReader br = null;

	public FileManipulation() {
	}

<<<<<<< HEAD
=======
	/**
	 * Get the corresponding buffered reader of a file
	 */
>>>>>>> 9c8df1751a5e1c886fe2cf0bca30f577ae100058
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

<<<<<<< HEAD
=======
	/**
	 * Close the resource associated with one FileManipulation instance
	 */
>>>>>>> 9c8df1751a5e1c886fe2cf0bca30f577ae100058
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