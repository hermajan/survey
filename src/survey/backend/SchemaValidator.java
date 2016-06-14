/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package survey.backend;

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

/**
 * Class SchemaValidator represents set of functions for xml files validation
 * @author Peter Petkanic
 */
public class SchemaValidator {
    
    private DocumentBuilder docBuilder;
    private String error;

    /**
     * Initializes schema validator
     * @param schemaName path to xml schema
     * @throws SAXException
     * @throws ParserConfigurationException 
     */
    public SchemaValidator(String schemaName) throws SAXException, ParserConfigurationException{
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
            throw ex;
        } catch (ParserConfigurationException ex) {
            System.err.println("Parser configuration error: " + ex.getMessage());
            throw ex;
        }
    }
    
    private class ValidationErrorsHandler implements ErrorHandler{
        @Override
        public void warning(SAXParseException exception) throws SAXException {
            Logger.getAnonymousLogger(SchemaValidator.class.getName()).log(Level.INFO,exception.getMessage());
        }


        @Override
        public void error(SAXParseException exception) throws SAXException {
            error = exception.getMessage();
            throw new SAXException(error);
        }

        @Override
        public void fatalError(SAXParseException exception) throws SAXException {
            error = exception.getMessage();
            throw new SAXException(error);
        }

    }
    
    
    /**
     * Validates xml file, if incorect, exception is thrown.
     * 
     * @param xmlFilename
     * @return true if file is correct
     * @throws IOException
     * @throws SAXException 
     */
    public boolean validate(String xmlFilename) throws IOException, SAXException{
        try {
            docBuilder.parse(new File(xmlFilename));
        } catch (SAXException ex) {
            throw ex;
        }
        return true;
    }
    
}
