package survey.frontend.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Peter Petkanič
 */
public class AnswersTableModel extends AbstractTableModel{
    
    /**
     * Representing Answer(id, string) in Survey
     */
    class Answer{
        private final int aid;
        private String answer;

        public Answer(int aid, String answer) {
            this.aid = aid;
            this.answer = answer;
        }
    }
    
    private final List<Answer> answers = new ArrayList<>();

    public AnswersTableModel() {
    }

    @Override
    public int getRowCount() {
        return answers.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Answer answer = answers.get(rowIndex);
        switch(columnIndex){
            case 0:
                return answer.aid;
            case 1:
                return answer.answer;
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "ID";
            case 1:
                return "Answer";
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }
    
    /**
     * Adds Answer to table, if Answer already exist, update it
     * @param aid ID of Answer
     * @param answer Text of Answer
     */
    public void addAnswerToTable(int aid, String answer){
        Answer newAnswer = new Answer(aid, answer);
        
        for(int i = 0; i < answers.size(); i++){
            Answer a = answers.get(i);
            if(a.aid == aid){
                a.answer = answer;
                return;
            }
        }
        answers.add(newAnswer);
        fireTableCellUpdated(answers.size(), getColumnCount());
    }
    
    /**
     * Returns Answer on index rowIndex
     * @param rowIndex Index of table row
     * @return Answer on row
     */
    public int getAnswerIdOnRow(int rowIndex){
        Answer answer = answers.get(rowIndex);
        return answer.aid;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 1:
            case 0:
                return false;
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }
    
    /**
     * Reloads Answers in table
     * @param answers Answers to be recreated
     */
    public void recreateAnswers(HashMap<Integer,String> answers){
        this.answers.clear();
        
        for (Map.Entry<Integer, String> entry : answers.entrySet()) {
            this.answers.add(new Answer(entry.getKey(), entry.getValue()));  
        }
           
        fireTableCellUpdated(answers.size(), getColumnCount());
    }
    
}
