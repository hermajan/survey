package survey;

import java.util.HashMap;
import java.util.Set;

public class Question {
    
    public enum QuestionType {
        CLOSED, MULTIPLE;
    }
    
    private int qid;
    private String description;
    private QuestionType qt;
    private HashMap<Integer, String> answers;
    
    public Question(int qid, String description, QuestionType qt) {
        this.qid = qid;
        this.description = description;
        this.qt = qt;
    }
    
    public void addAnswer(int aid, String text) {
        answers. put(aid, text);
    }
    
    public int getQID() {
        return qid;
    }
    
    public String getDescription() {
        return description;
    }

    public QuestionType getQuestionType() {
        return qt;
    }
    public Set<Integer> getAnswerIDs() {
        return answers.keySet();
    }
    
    public String getAnswer(int aid) {
        return answers.get(aid);
    }
}

 