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

    private static final String PATH = "../";

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

    private void validateBothValidInvalid(String xmlName){

        String fileName = xmlName.replaceAll(".xml","");

        File[] listOfFiles = new File(PATH + "invalid/" + fileName + "/").listFiles();

        if(listOfFiles != null) {
            for (File file : listOfFiles) {
                try {
                    if (this.validate(PATH + "invalid/" + fileName + "/" + file.getName()).contains("is valid")) {
                        System.out.println(PATH + "invalid/" + fileName + "/" + file.getName());
                        return;
                    }
                } catch (IOException ex) {
                    System.err.println("File not found: " + ex.getMessage());
                }
            }

            System.out.println("All " + xmlName + " files were invalid and should be invalid. [OK]");
        }


        listOfFiles = new File(PATH + "valid/" + fileName + "/").listFiles();

        if(listOfFiles != null) {
            for (File file : listOfFiles) {
                try {
                    String str = this.validate(PATH + "valid/" + fileName + "/" + file.getName());
                    if (str.contains("validation error")) {
                        System.out.println(str);
                        return;
                    }
                } catch (IOException ex) {
                    System.err.println("File not found: " + ex.getMessage());
                }
            }

            System.out.println("All " + xmlName + " files were valid and should be valid. [OK]");
        }
    }


    public static void main(String[] args) {
        new SchemaValidator(PATH + "survey.xsd").validateBothValidInvalid("survey.xml");
        new SchemaValidator(PATH + "response.xsd").validateBothValidInvalid("response.xml");
    }
}
