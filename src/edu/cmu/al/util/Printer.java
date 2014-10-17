package edu.cmu.al.util;

import java.io.*;

/**
 * Description: Used to print log or something to file
 * 
 * @author Kuo Liu
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

	/**
	 * Close the resource associated with the printer instance
	 */
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

	/**
	 * Print str to the file without a \n
	 */
	public void print(String str) {
		this.ps.print(str);
	}

	/**
	 * Print str to the file with a \n
	 */
	public void println(String str) {
		this.ps.println(str);
	}
}