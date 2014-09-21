package edu.cmu.al.util;

import java.io.*;

/**
 * Description: Used to print log or something to file
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

	public void print(String str) {
		this.ps.print(str);
	}

	public void println(String str) {
		this.ps.println(str);
	}
}