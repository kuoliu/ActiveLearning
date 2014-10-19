package edu.cmu.al.sampling;
/**
 * SamplingException is the exception related with the sampling process
 * @author yuanyuan
 *
 */
public class SamplingException extends Exception{
	
	private static final long serialVersionUID = -8935853021890344193L;

	//Parameterless Constructor
    public SamplingException() {}

    //Constructor that accepts a message
    public SamplingException(String message)
    {
       super(message);
    }
}