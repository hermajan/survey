package survey.frontend.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author peteru
 */
public class AnswersTableModel extends AbstractTableModel{
    class Answer{
        private final int aid;
        private final String answer;

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
    
    public void addAnswerToTable(int aid, String answer){
        answers.add(new Answer(aid, answer));
        fireTableCellUpdated(answers.size(), getColumnCount());
    }
    
    public String getAnswerStringOnRow(int rowIndex){
        Answer answer = answers.get(rowIndex);
        return answer.answer;
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
    
    public void recreateQuestions(HashMap<Integer,String> answers){
        this.answers.clear();
        
        for (Map.Entry<Integer, String> entry : answers.entrySet()) {
            this.answers.add(new Answer(entry.getKey(), entry.getValue()));  
        }
           
        fireTableCellUpdated(answers.size(), getColumnCount());
    }
    
}
