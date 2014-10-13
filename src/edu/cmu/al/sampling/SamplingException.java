package edu.cmu.al.sampling;
/**
 * SamplingException is the exception related with the sampling process
 * @author yuanyuan
 *
 */
public class SamplingException extends Exception{
	
	//Parameterless Constructor
    public SamplingException() {}

    //Constructor that accepts a message
    public SamplingException(String message)
    {
       super(message);
    }
}
