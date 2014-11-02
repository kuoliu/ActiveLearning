package edu.cmu.al.util;

public enum Constant {
	LR_FILE("logistic.txt"), SVM_FILE("svm.txt"), REG_FILE("regression.txt"),
	LR_COL("lr_confidence"), REG_COL("reg_confidence"), SVM_COL("svm_confidence");
	
	private String name;
	
	private Constant(String n){
		this.name = n;
	}
	
	public String getName(){
		return this.name;
	}
}
