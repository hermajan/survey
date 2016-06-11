/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokus2.backend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import pokus2.backend.entities.Question;
import pokus2.backend.entities.QuestionType;

import pokus2.backend.entities.Survey;

/**
 *
 * @author peteru
 */
public class SurveyEngineTest {
    public static void main(String[] args){
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
