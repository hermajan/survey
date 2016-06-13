/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package survey.frontend.panels;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumn;
import survey.backend.entities.Question;
import survey.backend.entities.QuestionType;
import survey.backend.entities.Survey;
import survey.frontend.models.AnswersTableModel;

/**
 *
 * @author Peter Petkanič
 */
public class jPanelQuestion extends javax.swing.JPanel {
	private static final long serialVersionUID = 1L;

    private final Survey survey;
    private final Question question;
    private int questionCount;
    
    
    /**
     * Creates new form jPanelAddQuestion
     * @param survey
     * @param question
     */
    public jPanelQuestion(Survey survey, Question question) {
        this.survey = survey;
        this.question = question;
        questionCount = this.question.getAnswerSize();
        questionCount++;
        
        initComponents();
        
        if(question.getQid() != -1){
            jTextFieldDescription.setText(question.getDescription());
            if(question.getQuestionType() == QuestionType.CLOSED){
                this.jRadioButtonClosed.setSelected(true);
            }else{
                this.jRadioButtonMultiple.setSelected(true);
            }
        }else{
            this.jRadioButtonClosed.setSelected(true);
        }
        updateTable();
        
    }
    
    /**
     * Recreating table of Answers
     */
    private void updateTable(){
        AnswersTableModel model  = (AnswersTableModel) jTableAnswers.getModel();
        model.recreateAnswers(question.getAnswers());
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jButtonAddAnswer = new javax.swing.JButton();
        jButtonSaveQuestion = new javax.swing.JButton();
        jTextFieldDescription = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableAnswers = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jRadioButtonMultiple = new javax.swing.JRadioButton();
        jRadioButtonClosed = new javax.swing.JRadioButton();
        jButtonEditAnswer = new javax.swing.JButton();
        jButtonBack = new javax.swing.JButton();

        jButtonAddAnswer.setText("Add answer");
        jButtonAddAnswer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddAnswerActionPerformed(evt);
            }
        });

        jButtonSaveQuestion.setText("Save");
        jButtonSaveQuestion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveQuestionActionPerformed(evt);
            }
        });

        jTableAnswers.setModel(new AnswersTableModel());
        jScrollPane1.setViewportView(jTableAnswers);
        jTableAnswers.setAutoResizeMode( JTable.AUTO_RESIZE_ALL_COLUMNS );
        TableColumn columnA = jTableAnswers.getColumn("ID");
        columnA.setMinWidth(50);
        columnA.setMaxWidth(50);

        jLabel1.setText("Question");

        buttonGroup1.add(jRadioButtonMultiple);
        jRadioButtonMultiple.setText("MULTIPLE");

        buttonGroup1.add(jRadioButtonClosed);
        jRadioButtonClosed.setText("CLOSED");

        jButtonEditAnswer.setText("Edit answer");
        jButtonEditAnswer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditAnswerActionPerformed(evt);
            }
        });

        jButtonBack.setText("Back");
        jButtonBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonAddAnswer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonEditAnswer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonSaveQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextFieldDescription)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(15, 406, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jRadioButtonClosed)
                                    .addComponent(jRadioButtonMultiple))
                                .addGap(34, 34, 34))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(54, 54, 54)
                                .addComponent(jButtonBack, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jRadioButtonClosed)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonMultiple)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 101, Short.MAX_VALUE)
                        .addComponent(jButtonBack))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSaveQuestion)
                    .addComponent(jButtonAddAnswer)
                    .addComponent(jButtonEditAnswer))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSaveQuestionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveQuestionActionPerformed
        if (jRadioButtonMultiple.isSelected()){
            question.setQuestionType(QuestionType.MULTIPLE);
        }else{
            question.setQuestionType(QuestionType.CLOSED);
        }
        
        if (question.getAnswers().size() < 2){
            JOptionPane.showMessageDialog(this, "Please add at least 2 answers!", "Error",
                                    JOptionPane.ERROR_MESSAGE);
        }else{
                question.setDescription(jTextFieldDescription.getText());
            if(question.getQid() == -1){
                question.setQid(survey.getQuestions().size() + 1);
                survey.addQuestion(question);         
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);        
                topFrame.dispose();
            }else{
                question.setDescription(jTextFieldDescription.getText());
                survey.editQuestion(question);
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);        
                topFrame.dispose();
            }
        }
    }//GEN-LAST:event_jButtonSaveQuestionActionPerformed

    private void jButtonAddAnswerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddAnswerActionPerformed
        JTextField field1 = new JTextField();
        JPanel panel = new JPanel(new GridLayout(0, 1));
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        panel.add(new JLabel("Answer"));
        panel.add(field1);   
        topFrame.setEnabled(false);
		
        int result = JOptionPane.showConfirmDialog(this, panel, "Add Answer",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            if(field1.getText().isEmpty()){
                JOptionPane.showMessageDialog(this, "Empty answer!", "Error",
                                    JOptionPane.ERROR_MESSAGE);
            }else{
                question.addAnswer(questionCount, field1.getText());                
                AnswersTableModel model  = (AnswersTableModel) jTableAnswers.getModel();
                model.addAnswerToTable(questionCount, field1.getText());
                model.fireTableDataChanged();
                questionCount += 1;
                updateTable();
            }     
        }
        topFrame.setEnabled(true);
    }//GEN-LAST:event_jButtonAddAnswerActionPerformed

    private void jButtonEditAnswerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditAnswerActionPerformed
        boolean success = false;
        AnswersTableModel model = (AnswersTableModel) jTableAnswers.getModel();
        int aid = 0;
        
        try{
            aid = model.getAnswerIdOnRow(jTableAnswers.getSelectedRow());
            success = true;
        }catch(ArrayIndexOutOfBoundsException e){
            JOptionPane.showMessageDialog(this, "Please select answer!", "Error", JOptionPane.ERROR_MESSAGE);   
        }
        
        if(success){
            JTextField field1 = new JTextField();
            field1.setText(question.getAnswer(aid));
            JPanel panel = new JPanel(new GridLayout(0, 1));
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            panel.add(new JLabel("Answer"));
            panel.add(field1);   
            topFrame.setEnabled(false);
            int result = JOptionPane.showConfirmDialog(this, panel, "Add Answer",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                if(field1.getText().isEmpty()){
                    JOptionPane.showMessageDialog(this, "Empty answer!", "Error",JOptionPane.ERROR_MESSAGE);
                }else{
                    question.addAnswer(aid, field1.getText());                
                    model.addAnswerToTable(questionCount, field1.getText());
                    model.fireTableDataChanged();
                    updateTable();
                }
            }
            topFrame.setEnabled(true);
        }
    }//GEN-LAST:event_jButtonEditAnswerActionPerformed

    private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackActionPerformed
        JFrame thisFrame = (JFrame)this.getRootPane().getParent();
        thisFrame.dispose();
    }//GEN-LAST:event_jButtonBackActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButtonAddAnswer;
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonEditAnswer;
    private javax.swing.JButton jButtonSaveQuestion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JRadioButton jRadioButtonClosed;
    private javax.swing.JRadioButton jRadioButtonMultiple;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableAnswers;
    private javax.swing.JTextField jTextFieldDescription;
    // End of variables declaration//GEN-END:variables
}
