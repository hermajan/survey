package survey.backend;

import survey.backend.entities.Question;
import java.io.File;
import java.io.IOException;
import static java.lang.Integer.max;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import survey.backend.entities.QuestionType;
import survey.backend.entities.Survey;

public class SurveyEngine {
    
    private final Document doc;
    private final String filePath;

   
   /**
    * Constructor. Opens already existing valid xml survey file.
    * 
    * @param filePath path to the file to be opened 
     * @throws survey.backend.SurveyEngineException 
    */
   public SurveyEngine(String filePath) throws SurveyEngineException {
       this.filePath = filePath;
       try {
           DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
           DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
           this.doc = dBuilder.parse(new File(filePath));
       } catch (ParserConfigurationException | SAXException | IOException e) {
           throw new SurveyEngineException("Can't open file " + filePath, e);
       }
       
       try {
           SchemaValidator validator = new SchemaValidator("src/xml/survey.xsd");
           validator.validate(this.filePath);
       } catch (SAXException | ParserConfigurationException | IOException e) {
           throw new SurveyEngineException("Validation failed!", e);
       }
   }
   
   
   private Element getSurveyElementBySid(int surveyId){
        NodeList surveys = doc.getElementsByTagName("survey");

        for (int k = 0; k < surveys.getLength(); k++) {
            Element s = (Element) surveys.item(k);
            int sid = Integer.parseInt(s.getAttribute("sid"));
            if (surveyId == sid) {
                return s;
            }
        }

        throw new NullPointerException("Survey with id " + surveyId + " does not exists.");
   }
   
   /**
    * Finds survey element with given ID, loads it's data into the Survey class
    * instance. If element is not found, exception is thrown.
    * 
    * @param surveyId ID of the element
    * @return Survey class with all element's contents loaded
     * @throws survey.backend.SurveyEngineException
    */
   public Survey getSurvey(int surveyId) throws SurveyEngineException {
        Element survey = null;
       
        try { 
            survey = getSurveyElementBySid(surveyId);
        } catch (NullPointerException e) {
            throw new SurveyEngineException(e.getMessage());
        }
        
        try {    
            List<Question> questions = new ArrayList<>();
            
            //get Questions of survey
            NodeList questionList = survey.getElementsByTagName("question");
            for (int i = 0; i < questionList.getLength(); i++) {
                Element questionElement = (Element) questionList.item(i);
                int qid = Integer.parseInt(questionElement.getAttribute("qid"));
                String description = questionElement.getElementsByTagName("description").item(0).getTextContent();
                String qTString  = questionElement.getAttribute("type");
                QuestionType qt = null;
                switch (qTString) {
                    case "closed":
                        qt = QuestionType.CLOSED;
                        break;
                    case "multiple":
                        qt = QuestionType.MULTIPLE;
                        break;
                }
                Question question = new Question(qid, description, qt);
                
                //get Answers of survey
                NodeList answersList = questionElement.getElementsByTagName("answer");
                for (int j = 0; j < answersList.getLength(); j++) {
                    Element answerElement = (Element) answersList.item(j);
                    int aid = Integer.parseInt(answerElement.getAttribute("aid"));
                    String text = answerElement.getTextContent();
                    question.addAnswer(aid, text);
                }

                questions.add(question);
            }
            return new Survey(
                    Integer.parseInt(survey.getAttribute("sid")), 
                    survey.getElementsByTagName("title").item(0).getTextContent(), 
                    survey.getElementsByTagName("description").item(0).getTextContent(), 
                    questions);
        } catch (NumberFormatException | DOMException e) {
            throw new SurveyEngineException(e.getMessage());
        }
   }
   
   
   /**
    * Saves given survey into the xml file. If a survey with the same ID already
    * exists it's deleted and replaced otherwise a new valid free ID is 
    * found for it.
    * 
    * @param survey survey information to be saved
     * @throws survey.backend.SurveyEngineException 
    */
    public void saveSurvey(Survey survey) throws SurveyEngineException{
        surveyValidation(survey);
        
        Element surveysElement = (Element) doc.getElementsByTagName("surveys").item(0);
        NodeList surveysElements = surveysElement.getElementsByTagName("survey");
        Element surveyElement;
        
        int sid = survey.getSid();
        try {
            surveyElement = getSurveyElementBySid(sid);
            surveysElement.removeChild(surveyElement);
        }
        catch(NullPointerException e) {
            int maxId = 0;
            for (int i = 0; i < surveysElements.getLength(); i++) {
                int id = Integer.parseInt(((Element)surveysElements.item(i)).getAttribute("sid"));
                maxId = max(id, maxId);
            }
            sid = ++maxId;
        }
        
        surveyElement = doc.createElement("survey");
        surveyElement.setAttribute("sid", Integer.toString(sid));
        Element titleElement = doc.createElement("title");
        titleElement.setTextContent(survey.getTitle());
        surveyElement.appendChild(titleElement);
        Element descriptionElement = doc.createElement("description");
        descriptionElement.setTextContent(survey.getDescription());
        surveyElement.appendChild(descriptionElement);
        
        Element questionsElement = doc.createElement("questions");
        for (Question question: survey.getQuestions()) {
            Element questionElement = doc.createElement("question");
            questionElement.setAttribute("qid", Integer.toString(question.getQid()));
            
            descriptionElement = doc.createElement("description");
            descriptionElement.setTextContent(question.getDescription());
            questionElement.appendChild(descriptionElement);
            
            String qTString = null;
            switch (question.getQuestionType()) {
                case CLOSED: 
                    qTString = "closed"; 
                    break;
                case MULTIPLE: 
                    qTString = "multiple"; 
                    break;
            }
            questionElement.setAttribute("type", qTString);
            
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
        surveysElement.appendChild(surveyElement);
        
        {
            try {
                TransformerFactory tFactory = TransformerFactory.newInstance();
                Transformer transformer = tFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(this.filePath));
                transformer.transform(source, result);
            } catch (TransformerException e) {
                throw new SurveyEngineException("Survey could not be saved");
            }
        }
    }
    
    
    /**
     * Finds all surveys and returns their IDs with their titles.
     * 
     * @return map with IDs and titles
     */
    public Map<Integer, String> getSurveysIdsTitles() {
        HashMap<Integer, String> surveys = new HashMap<>();
        NodeList surveysElements = doc.getElementsByTagName("survey");
        
        for (int i = 0; i < surveysElements.getLength(); i++) {
            Element surveyElement = (Element) surveysElements.item(i);
            int sid = Integer.parseInt(surveyElement.getAttribute("sid"));
            String title = surveyElement.getElementsByTagName("title").item(0).getTextContent();
            surveys.put(sid, title);
        }
        
       return surveys;
    }
    
    private void surveyValidation(Survey survey) throws SurveyEngineException {
        if ("".equals(survey.getTitle())) {
            throw new SurveyEngineException("Survey title is empty or null");
        }
        if ("".equals(survey.getDescription())) {
            throw new SurveyEngineException("Survey description is empty or null");
        }
        if (survey.getQuestions().isEmpty()) {
            throw new SurveyEngineException("No questions in survey");
        }
        HashSet<Integer> ids = new HashSet<>();
        for (Question question: survey.getQuestions()) {
            if (!ids.add(question.getQid())) {
                throw new SurveyEngineException("Multiple Question ids");
            }
            questionValidation(question);
        }
    }
    
    private void questionValidation(Question question) throws SurveyEngineException {
        if ("".equals(question.getDescription())) {
            throw new SurveyEngineException("Question description empty or null");
        }
        HashMap<Integer, String> answers = question.getAnswers();
        if (answers.size() < 2) {
            throw new SurveyEngineException("Question has less than 2 answers");
        }
        for (int i: answers.keySet()) {
            if ("".equals(answers.get(i))) {
                throw new SurveyEngineException("Answer for a question is empty or null");
            }
        } 
    }
}


