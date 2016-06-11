/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package survey.backend;

import survey.backend.entities.Survey;

/**
 *
 * @author peteru
 */
public class SurveyEngineTest {
    public static void main(String[] args) throws SurveyEngineException{
        SurveyEngine se = null;
        try {
            se = new SurveyEngine("test.xml");
            Survey s = se.getSurvey(1);
            se.saveSurvey(s);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
        
        Survey s = se.getSurvey(1);
    }
}
