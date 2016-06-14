/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package survey;
 
 
import java.util.ArrayList;
import org.junit.Test;
import survey.backend.SurveyEngine;
import survey.backend.SurveyEngineException;
import survey.backend.entities.Survey;
import static junit.framework.TestCase.assertEquals;
import survey.backend.entities.Question;
import survey.backend.entities.QuestionType;
 
/**
 * Contains few tests for SurveyEngine.
 *
 * @author Martin
 */
public class SurveyEngineTest {
       
    /**
     * Tests loading surveys.
     *
     * @throws SurveyEngineException
     */
    @Test
    public void getSurveyTest() throws SurveyEngineException {
        SurveyEngine se = new SurveyEngine("test/survey/surveytest.xml");
     
        Survey s = se.getSurvey(1);
        assertEquals(s.getSid(), 1);
    }
   
   
    /**
     * Tests saving of an incorrect survey. Survey has a question with no
     * answers.
     *
     * @throws SurveyEngineException
     */
    @Test(expected=SurveyEngineException.class)
    public void saveIncorrectTest() throws SurveyEngineException {
        SurveyEngine se = new SurveyEngine("test/survey/surveytest.xml");
       
        Question q = new Question(1, "mas rad jablka?", QuestionType.CLOSED);
        ArrayList<Question> qs = new ArrayList<>();
        qs.add(q);
        Survey s = new Survey(5, "test survey", "some description", qs);
        se.saveSurvey(s);
    }
   
    /**
     * Tests repetitive loading, changing and saving of a survey.
     *
     * @throws SurveyEngineException
     */
    @Test
    public void saveTest() throws SurveyEngineException {
        SurveyEngine se = new SurveyEngine("test/survey/surveytest.xml");
       
        Survey s = se.getSurvey(1);
        s.setTitle("Halo?");
        se.saveSurvey(s);
       
        s = se.getSurvey(1);
        assertEquals("Halo?", s.getTitle());
       
       
        s.setTitle("Ahoj");
        se.saveSurvey(s);
       
        s = se.getSurvey(1);
        assertEquals("Ahoj", s.getTitle());
    }
}