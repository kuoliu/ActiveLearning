package edu.cmu.al.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class testPrinter {

	@Test
	public void test() {
		Printer pr = new Printer(Configuration.getTmpPath());
		pr.print("");
		pr.println("");
		pr.close();
	}

}
