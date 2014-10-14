package edu.cmu.al.util;

import java.io.*;

/**
 * Description: Used to print log or something to file
<<<<<<< HEAD
=======
 * 
 * @author Kuo Liu
>>>>>>> 9c8df1751a5e1c886fe2cf0bca30f577ae100058
 */
public class Printer {

	private FileOutputStream fos = null;
	private PrintStream ps = null;

	public Printer(String file) {
		try {
			this.fos = new FileOutputStream(file);
			this.ps = new PrintStream(this.fos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

<<<<<<< HEAD
=======
	/**
	 * Close the resource associated with the printer instance
	 */
>>>>>>> 9c8df1751a5e1c886fe2cf0bca30f577ae100058
	public void close() {
		try {
			if (this.ps != null)
				this.ps.close();
			if (this.fos != null)
				this.fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

<<<<<<< HEAD
=======
	/**
	 * Print str to the file without a \n
	 */
>>>>>>> 9c8df1751a5e1c886fe2cf0bca30f577ae100058
	public void print(String str) {
		this.ps.print(str);
	}

<<<<<<< HEAD
=======
	/**
	 * Print str to the file with a \n
	 */
>>>>>>> 9c8df1751a5e1c886fe2cf0bca30f577ae100058
	public void println(String str) {
		this.ps.println(str);
	}
}