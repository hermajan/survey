package survey;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Management of XML files.
 */
public class XMLmanagement {
	/**
	 * XML document.
	 */
	private Document xml;

	public Document getXml() {
		return xml;
	}

	/**
	 * Constuctor of XMLmanagement.
	 * @throws ParserConfigurationException
	 */
	public XMLmanagement() throws ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbf.newDocumentBuilder();
		xml = builder.newDocument();
		xml.toString();
	}
	
	public XMLmanagement(Document doc) {
		this.xml = doc;
	}
	
	/**
	 * Returns XML document in string.
	 * @return XML document in string.
	 * @throws TransformerException 
	 */
	public String restringing() throws TransformerException {
		Transformer tf = TransformerFactory.newInstance().newTransformer();
		tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		tf.setOutputProperty(OutputKeys.INDENT, "yes");
		tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		Writer out = new StringWriter();
		tf.transform(new DOMSource(xml), new StreamResult(out));
		return out.toString();
	}
	
	/**
	 * Exports XML to the file.
	 * @param fileName Name of file for exporting.
	 * @throws TransformerConfigurationException
	 * @throws FileNotFoundException
	 * @throws TransformerException
	 */
	public void exporting(String fileName) throws TransformerConfigurationException, FileNotFoundException, TransformerException {
		Transformer tf = TransformerFactory.newInstance().newTransformer();
		tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		tf.setOutputProperty(OutputKeys.INDENT, "yes");
		tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        DOMSource source = new DOMSource(xml);
        StreamResult result = new StreamResult(new FileOutputStream(fileName));
        tf.transform(source, result);
	}
	
	/**
	 * Imports XML from the file.
	 * @param fileName Name of file for importing.
	 * @throws javax.xml.parsers.ParserConfigurationException 
	 * @throws org.xml.sax.SAXException 
	 * @throws java.io.IOException 
	 */
	public void importing(String fileName) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        xml = builder.parse(fileName);
	}
}
