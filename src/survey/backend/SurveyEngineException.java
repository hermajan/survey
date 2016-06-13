/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokus2.backend;

/**
 * Exception used in SurveyEngine.
 * 
 * @author Martin
 */
public class SurveyEngineException extends Exception{

    public SurveyEngineException(String message) {
        super(message);
    }

    public SurveyEngineException(String message, Throwable cause) {
        super(message, cause);
    }
    
}