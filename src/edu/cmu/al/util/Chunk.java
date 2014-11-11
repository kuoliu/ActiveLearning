/**
 * Description: 
 * 	Data structure for a chunk, which contains three parts:
 * 		word	the word itself
 * 		pos		the POS Tagging of the word
 * 		ne		if the word is part of a named entity
 */
package edu.cmu.al.util;

public class Chunk {

	private String word; // The word token itself
	private String pos; // The POS Tagging of the word
	private String ne; // If the word isn't NE, then it is "O"

	public Chunk() {
	}

	public Chunk(String word, String pos, String ne) {
		this.setWord(word);
		this.setPos(pos);
		this.setNe(ne);
	}

	public boolean beginWithNN() {
		return pos.startsWith("NN");
	}

	public String getNe() {
		return ne;
	}

	public String getPos() {
		return pos;
	}

	public String getWord() {
		return word;
	}

	public boolean isNe() {
		return !ne.equals("O");
	}

	public void setNe(String ne) {
		this.ne = ne;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String toString() {
		return "{" + this.getWord() + "," + this.getPos() + "," + this.getNe()
				+ "}";
	}
}