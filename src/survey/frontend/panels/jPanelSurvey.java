/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package survey.frontend.panels;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumn;
import survey.backend.SurveyEngine;
import survey.backend.SurveyEngineException;
import survey.backend.entities.Question;
import survey.backend.entities.QuestionType;
import survey.backend.entities.Survey;
import survey.frontend.models.QuestionTableModel;

/**
 *
 * @author Peter Petkanič
 */
public class jPanelSurvey extends javax.swing.JPanel {
	private static final long serialVersionUID = 1L;

    private final SurveyEngine se;
    private final Survey survey;
    
    
    /**
     * Creates new form jPanelCreate
     * @param se
     * @param survey
     */
    public jPanelSurvey(SurveyEngine se, Survey survey) {
        this.se = se;
        this.survey = survey;
        
        initComponents();
       
        if(survey.getSid() != -1){           
            jTextFieldTitle.setText(survey.getTitle());
            jTextFieldDescription.setText(survey.getDescription());
        }else{
            survey.setQuestions(new ArrayList<>());
        }
        
        if(!survey.getQuestions().isEmpty()){
            updateTable();
        }

    }

    /**
     * Recreating table of Questions
     */
    private void updateTable(){
        QuestionTableModel model  = (QuestionTableModel) jTableQuestions.getModel();
        model.recreateQuestions(survey.getQuestions());
        model.fireTableDataChanged();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonSaveSurvey = new javax.swing.JButton();
        jButtonAddQuestion = new javax.swing.JButton();
        jLabelSurveyTitle = new javax.swing.JLabel();
        jLabelDescription = new javax.swing.JLabel();
        jTextFieldTitle = new javax.swing.JTextField();
        jTextFieldDescription = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableQuestions = new javax.swing.JTable();
        jLabelQuestions = new javax.swing.JLabel();
        jButtonEditQuestion = new javax.swing.JButton();
        jButtonBack = new javax.swing.JButton();

        jButtonSaveSurvey.setText("Save survey");
        jButtonSaveSurvey.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveSurveyActionPerformed(evt);
            }
        });

        jButtonAddQuestion.setText("Add question");
        jButtonAddQuestion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddQuestionActionPerformed(evt);
            }
        });

        jLabelSurveyTitle.setText("Survey Title");

        jLabelDescription.setText("Description");

        jTableQuestions.setModel(new QuestionTableModel());
        jScrollPane1.setViewportView(jTableQuestions);
        jTableQuestions.setAutoResizeMode( JTable.AUTO_RESIZE_ALL_COLUMNS );
        TableColumn columnA = jTableQuestions.getColumn("ID");
        columnA.setMinWidth(50);
        columnA.setMaxWidth(50);

        jLabelQuestions.setText("Questions");

        jButtonEditQuestion.setText("Edit question");
        jButtonEditQuestion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditQuestionActionPerformed(evt);
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
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelSurveyTitle)
                    .addComponent(jLabelDescription)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jTextFieldTitle, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(1, 1, 1)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 574, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelQuestions)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(131, 131, 131)
                                                .addComponent(jButtonEditQuestion))
                                            .addComponent(jButtonAddQuestion))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButtonBack, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButtonSaveSurvey, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(jTextFieldDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 574, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabelSurveyTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelDescription)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelQuestions)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSaveSurvey)
                    .addComponent(jButtonAddQuestion)
                    .addComponent(jButtonEditQuestion)
                    .addComponent(jButtonBack))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Starts new frame for Question editing
     * @param panel
     * @param frameName 
     */
    private void questionEditorInit(JPanel panel, String frameName){
        JFrame newFrame = new JFrame(frameName);
		newFrame.setIconImage(new ImageIcon("src/web/favicon.png").getImage());
        newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newFrame.setLayout(new BorderLayout());
        newFrame.add(panel, BorderLayout.CENTER);
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        newFrame.setLocation(topFrame.getX(),topFrame.getY());
        newFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                updateTable();
                topFrame.setEnabled(true);
            }
        });
        topFrame.setEnabled(false);
        newFrame.pack();
        newFrame.setVisible(true);
    }
    
    private void jButtonAddQuestionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddQuestionActionPerformed
        questionEditorInit(new jPanelQuestion(survey, new Question(-1, "", QuestionType.CLOSED)), "Add question");
    }//GEN-LAST:event_jButtonAddQuestionActionPerformed

    private void jButtonEditQuestionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditQuestionActionPerformed
        try {    
            QuestionTableModel model = (QuestionTableModel) jTableQuestions.getModel();
            Question q = model.getQuestionOnRow(jTableQuestions.getSelectedRow());
            questionEditorInit(new jPanelQuestion(survey, q), "Edit question");
        }catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Please select question first!", "Error",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonEditQuestionActionPerformed

    private void jButtonSaveSurveyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveSurveyActionPerformed
         
        survey.setDescription(jTextFieldDescription.getText());
        survey.setTitle(jTextFieldTitle.getText());
        
        boolean success = false;
        
        try {
            se.saveSurvey(survey);
            success = true;
        } catch (SurveyEngineException ex) {
             JOptionPane.showMessageDialog(this, ex.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
        } finally {
            if (success){
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);        
                topFrame.dispose();
            }
        }
    }//GEN-LAST:event_jButtonSaveSurveyActionPerformed

    private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackActionPerformed
        JFrame thisFrame = (JFrame)this.getRootPane().getParent();
        thisFrame.dispose();
    }//GEN-LAST:event_jButtonBackActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAddQuestion;
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonEditQuestion;
    private javax.swing.JButton jButtonSaveSurvey;
    private javax.swing.JLabel jLabelDescription;
    private javax.swing.JLabel jLabelQuestions;
    private javax.swing.JLabel jLabelSurveyTitle;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableQuestions;
    private javax.swing.JTextField jTextFieldDescription;
    private javax.swing.JTextField jTextFieldTitle;
    // End of variables declaration//GEN-END:variables
}
