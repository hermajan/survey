package survey.backend.entities;

import java.util.List;

public class Survey {
    private final int sid;
    private String title;
    private String description;
    private List<Question> questions;

    public Survey(int sid, String title, String description, List<Question> questions) {
        this.sid = sid;
        this.title = title;
        this.description = description;
        this.questions = questions;
    }

    public int getSid() {
        return sid;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }     
}
