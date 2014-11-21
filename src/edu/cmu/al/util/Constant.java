package edu.cmu.al.util;

public enum Constant {
	LR_RANDOM_FILE("logistic_random.txt"), SVM_RANDOM_FILE("svm_random.txt"), REG_FILE("regression.txt"),
	LR_COL("lr_confidence"), REG_COL("reg_confidence"), SVM_COL("svm_confidence"),
	LR_UNCERTAIN_FILE("logistic_uncertain.txt"), SVM_UNCERTAIN_FILE("svm_uncertain.txt"),
	LR_QBC_FILE("logistic_QBC.txt"), SVM_QBC_FILE("svm_QBC.txt"),;
	
	private String name;
	
	private Constant(String n){
		this.name = n;
	}
	
	public String getName(){
		return this.name;
	}
}
