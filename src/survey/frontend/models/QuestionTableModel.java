package pokus2.frontend.models;
 
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import pokus2.backend.entities.Question;
 

public class QuestionTableModel extends AbstractTableModel{
    private final List<Question> questions = new ArrayList<>();
 
    public QuestionTableModel() {
    }
 
    @Override
    public int getRowCount() {
        return questions.size();
    }
 
    @Override
    public int getColumnCount() {
        return 3;
    }
 
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Question question = questions.get(rowIndex);
        switch(columnIndex){
            case 0:
                return question.getQid();
            case 1:
                return question.getDescription();
            case 2:
                return question.getQuestionType();
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }
   
    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Qid";
            case 1:
                return "Question";
            case 2:
                return "Question Type";
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }
    
    public Question getQuestionOnRow(int rowIndex){
        Question question = questions.get(rowIndex);
        return question;
    }
   
    public void addQuestionToTable(Question question){
        questions.add(question);
        fireTableCellUpdated(question.getSize(), question.getSize());
    }
 
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 1:
            case 2:
            case 0:
                return false;
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }
   
}
