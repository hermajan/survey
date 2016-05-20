/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package survey;

/**
 *
 * @author jakub
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Survey {
   private Document doc = null;
   public static final String FILEPATH = "surveystest.xml";
   
   public Survey() {
       try {
           DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
           DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
           doc = dBuilder.parse(new File(FILEPATH));
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
   
   public List<Question> openSurvey(int sid) {
       try {
           NodeList surveys = doc.getElementsByTagName("survey");
           int k = surveys.getLength() - 1;
           for (; k >= 0; k--) {
               Element survey = (Element) surveys.item(k);
               int side = Integer.getInteger(survey.getAttribute("sid"));
               if (sid == side) {
                   break;
               }
           }
           
           if (k == -1) {
               return new ArrayList<>();
           }
           
           Element survey = (Element) surveys.item(k);
           
           List<Question> questions = new ArrayList<>();
           NodeList questionList = survey.getElementsByTagName("question");
           for (int i = 0; i < questionList.getLength(); i++) {
               Element questionElement = (Element) questionList.item(i);
               int qid = Integer.parseInt(questionElement.getAttribute("qid"));
               String description = questionElement.getElementsByTagName("description").item(0).getTextContent();
               String qTString  = questionElement.getElementsByTagName("questionType").item(0).getTextContent();
               Question.QuestionType qt = null;
               switch(qTString) {
                   case "closed": qt = Question.QuestionType.CLOSED;
                   case "multiple": qt = Question.QuestionType.MULTIPLE;
               }
               Question question = new Question(qid, description, qt);
               NodeList answersList = questionElement.getElementsByTagName("answer");
               for (int j = 0; j < answersList.getLength(); j++) {
                   Element answerElement = (Element) answersList.item(i);
                   int aid = Integer.parseInt(answerElement.getAttribute("aid"));
                   String text = answerElement.getTextContent();
                   question.addAnswer(aid, text);
               }
           }
           return questions;
       } catch (Exception e) {
           e.printStackTrace();
           return null;
       }
   }
   
    public void saveSurvey(int sid, String title, String description, List<Question> questions){
        Element surveysElement = (Element) doc.getElementsByTagName("surveys").item(0);
        NodeList surveysElements = surveysElement.getElementsByTagName("survey");
        Element surveyElement = null;
        
        int k = surveysElements.getLength() - 1;
        for (; k >= 0; k--) {
            surveyElement = (Element) surveysElements.item(k);
            int side = Integer.getInteger(surveyElement.getAttribute("sid"));
            if (sid == side) {
                break;
            }
        }

        if (k != -1) {
            surveysElement.removeChild(surveyElement);
        }
        
        surveyElement = doc.createElement("survey");
        Element titleElement = doc.createElement("title");
        titleElement.setTextContent(title);
        surveyElement.appendChild(titleElement);
        Element descriptionElement = doc.createElement("description");
        descriptionElement.setTextContent(description);
        surveyElement.appendChild(descriptionElement);
        
        Element questionsElement = doc.createElement("questions");
        for (Question question: questions) {
            Element questionElement = doc.createElement("question");
            questionElement.setAttribute("qid", Integer.toString(question.getQID()));
            
            descriptionElement = doc.createElement("description");
            descriptionElement.setTextContent(question.getDescription());
            questionElement.appendChild(descriptionElement);
            
            Element typeElement = doc.createElement("type");
            String qTString = null;
            switch (question.getQuestionType()) {
                case CLOSED: qTString = "closed";
                case MULTIPLE: qTString = "multiple";
            }
            typeElement.setTextContent(qTString);
            questionElement.appendChild(typeElement);
            
            Element answersElement = doc.createElement("answers");
            
            for(int aid: question.getAnswerIDs()) {
                Element answerElement = doc.createElement("answer");
                answerElement.setAttribute("aid", Integer.toString(aid));
                answerElement.setTextContent(question.getAnswer(aid));
                answersElement.appendChild(answerElement);
            }
            
            questionElement.appendChild(answersElement);
            questionsElement.appendChild(questionElement);
        }
        
        surveyElement.appendChild(questionsElement);
        
        StreamResult result = new StreamResult(new File(FILEPATH));
    }
}