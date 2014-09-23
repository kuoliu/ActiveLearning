package edu.cmu.al.feature;

import java.util.ArrayList;

import edu.cmu.al.util.AmazonReview;

/**
 *  
 * For the BasicFeature, we choose two feature which are the number of the
 * people reviews and average score for all the people. 
 * @author Kuo Liu, Bo Ma
 */

public class BasicFeauture extends Feature {
	  /**
	   * Compute the average score for all the reviews for certain product.
	   * 
	   * @param reviews
	   *          All people's reviews for each product
	   * @return Average score for certain product.
	   */
	public Double computeAverageScore(ArrayList<AmazonReview> reviews) {
		return null;
	}
	/**
	   * Compute the number of reviews for certain product.
	   * 
	   * @param reviews
	   *          All people's reviews for each product
	   * @return  Certain product number of revies.
	   */
	public Integer computeNumRevies(ArrayList<AmazonReview> reviews)
	{
		return null;
	}
}
