package edu.cmu.al.ml;
/**
 * ClassificationException is the exception related with the training and test process
 * 
 * @author chenying
 *
 */
class ClassificationException extends Exception
{
      //Parameterless Constructor
      public ClassificationException() {}

      //Constructor that accepts a message
      public ClassificationException(String message)
      {
         super(message);
      }
 }
