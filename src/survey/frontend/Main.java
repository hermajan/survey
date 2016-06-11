package pokus2.frontend;

import java.awt.BorderLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import pokus2.backend.SurveyEngine;
import pokus2.backend.SurveyEngineException;
import pokus2.frontend.panels.jPanelMain;
import pokus2.frontend.panels.jPanelSurvey;


public class Main {
    public static void main(String[] args) {
        
        SurveyEngine surveyEngine = null;
        
        try {
            surveyEngine = new SurveyEngine("test.xml");
        } catch (SurveyEngineException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            //System.exit(-1); TO DO
        }
        
        final SurveyEngine se = surveyEngine;
        
        javax.swing.SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Survey creator");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            frame.add(new jPanelMain(se), BorderLayout.CENTER);
            frame.pack();
            frame.setVisible(true);
        });
        
        
        
        
        
    }
}
