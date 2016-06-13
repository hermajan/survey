package survey.frontend.models;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.table.AbstractTableModel;
import survey.backend.entities.Survey;
 
/**
 * 
 * @author Jakub Gavlas
 */
public class SurveyTableModel extends AbstractTableModel{
    private final List<Survey> surveys = new ArrayList<>();
 
    public SurveyTableModel() {
    }
    
    /**
     * Reloads Surveys in table
     * @param surveys Surveys to be recreated
     */
    public void recreateSurveys(Map<Integer,String> surveys){
        this.surveys.clear();
        surveys.entrySet().stream().forEach((entry) -> {
            this.surveys.add(new Survey(
                    entry.getKey(), entry.getValue(), "", null));
        });
        
        fireTableCellUpdated(surveys.size(), getColumnCount());
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "ID";
            case 1:
                return "Title";
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }
 
    @Override
    public int getRowCount() {
        return surveys.size();
    }
 
    @Override
    public int getColumnCount() {
        return 2;
    }
 
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Survey survey = surveys.get(rowIndex);
        switch(columnIndex){
            case 0:
                return survey.getSid();
            case 1:
                return survey.getTitle();
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }
    /**
    * Returns Survey on index rowIndex
    * @param rowIndex Index of table row
    * @return Survey on row
    */
    public Survey getSurveyOnRow(int rowIndex){
        Survey survey = surveys.get(rowIndex);
        return survey;
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
