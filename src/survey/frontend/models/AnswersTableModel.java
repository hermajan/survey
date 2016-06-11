package pokus2.frontend.models;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.HashMap;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author peteru
 */
public class AnswersTableModel extends AbstractTableModel{
    private final HashMap<Integer, String> answers = new HashMap<>();

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
        String answer = answers.get(rowIndex);
        switch(columnIndex){
            case 0:
                return rowIndex;
            case 1:
                return answer;
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Aid";
            case 1:
                return "Answer";
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }
    
    public void addAnswerToTable(String answer){
        answers.put(answers.size()+1, answer);
        fireTableCellUpdated(answers.size(), answers.size());
    }
    
    public String getAnswerOnRow(int rowIndex){
        String answer = answers.get(rowIndex);
        return answer;
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
    
}
