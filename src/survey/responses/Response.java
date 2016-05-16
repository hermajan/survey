package survey.responses;

import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Management of the response.
 */
public class Response {
	private Document doc;

	public Document getDoc() {
		return doc;
	}

	public Response() throws ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbf.newDocumentBuilder();
		doc = builder.newDocument();
	}
	
	/**
	 * Creates XML with response.
	 * @param sid ID of survey.
	 * @param questionsAnswers Map of questions and answers.
	 */
	public void createResponse(int sid, Map<Integer, List<Integer>> questionsAnswers) {
		Element responses;
		if(doc.getElementsByTagName("responses").getLength() == 0) {
			responses = doc.createElement("responses");
			responses.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			responses.setAttribute("xsi:noNamespaceSchemaLocation", "response.xsd");
		}
		else {
			responses = (Element)doc.getElementsByTagName("responses").item(0);
		}
		
		Element response = doc.createElement("response");
		
		Element surveyId = doc.createElement("surveyId");
		surveyId.setTextContent(Integer.toString(sid));
		response.appendChild(surveyId);
		
		Element questions = doc.createElement("questions");

		for(Map.Entry<Integer, List<Integer>> entry:questionsAnswers.entrySet()) {
			Integer qid = entry.getKey();

			Element question = doc.createElement("question");
			Element questionId = doc.createElement("questionId");
			questionId.setTextContent(Integer.toString(qid));
			question.appendChild(questionId);
			
			Element answers = doc.createElement("answers");
			for(Integer aid : entry.getValue()) {
				Element answerId = doc.createElement("answerId");
				answerId.setTextContent(Integer.toString(aid));
				answers.appendChild(answerId);
			}
			
			question.appendChild(answers);
			questions.appendChild(question);
		}

		response.appendChild(questions);
		responses.appendChild(response);
		
		if(doc.getElementsByTagName("responses").getLength() == 0) {
			doc.appendChild(responses);
		}
	}

	public static final void printXML(Document xml) throws TransformerException {
		Transformer tf = TransformerFactory.newInstance().newTransformer();
		tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		tf.setOutputProperty(OutputKeys.INDENT, "yes");
		tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		Writer out = new StringWriter();
		tf.transform(new DOMSource(xml), new StreamResult(out));
		System.out.println(out.toString());
	}
}
