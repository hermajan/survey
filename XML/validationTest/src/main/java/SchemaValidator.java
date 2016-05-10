import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


public class SchemaValidator {

    private DocumentBuilder docBuilder;
    private String error;

    private class ValidationErrorsHandler implements ErrorHandler{
        public void warning(SAXParseException exception) throws SAXException {
            Logger.getAnonymousLogger(SchemaValidator.class.getName()).log(Level.INFO,exception.getMessage());
        }


        public void error(SAXParseException exception) throws SAXException {
            error = exception.getMessage();
            throw new SAXException(error);
        }


        public void fatalError(SAXParseException exception) throws SAXException {
            error = exception.getMessage();
            throw new SAXException(error);
        }


    }

    private SchemaValidator(String schemaName){
        try {
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(new File(schemaName));

            DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);

            dbf.setSchema(schema);
            docBuilder = dbf.newDocumentBuilder();
            docBuilder.setErrorHandler(new ValidationErrorsHandler());
        } catch (SAXException ex) {
            System.err.println("Invalid schema: " + ex.getMessage());
            System.exit(-1);
        } catch (ParserConfigurationException ex) {
            System.err.println("Parser configuration error: " + ex.getMessage());
        }
    }

    private String validate(String xmlFilename) throws IOException{
        try {
            docBuilder.parse(new File(xmlFilename));
        } catch (SAXException ex) {
            return xmlFilename + " validation error: " + ex.getMessage();
        }
        return xmlFilename + " is valid";
    }

    public static void main(String[] args) {

        SchemaValidator validator = new SchemaValidator("../XML/survey/survey.xsd");

        File[] listOfFiles = new File("../XML/survey/invalid").listFiles();


        if(listOfFiles != null) {
            for (File file : listOfFiles) {
                try {
                    if (validator.validate("../XML/survey/invalid/" + file.getName()).contains("is valid")) {
                        System.out.println("../XML/survey/invalid/" + file.getName());
                        return;
                    }
                } catch (IOException ex) {
                    System.err.println("File not found: " + ex.getMessage());
                }
            }

            System.out.println("All files were invalid and should be invalid. [OK]");
        }


        listOfFiles = new File("../XML/survey/valid").listFiles();

        if(listOfFiles != null) {
            for (File file : listOfFiles) {
                try {
                    if (validator.validate("../XML/survey/valid/" + file.getName()).contains("validation error")) {
                        System.out.println("../XML/survey/valid/" + file.getName());
                        return;
                    }
                } catch (IOException ex) {
                    System.err.println("File not found: " + ex.getMessage());
                }
            }

            System.out.println("All files were valid and should be valid. [OK]");
        }
    }
}
