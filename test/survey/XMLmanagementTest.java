package survey;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import junit.framework.TestCase;
import org.xml.sax.SAXException;

/**
 * Tests for the XMLmanagement class.
 */
public class XMLmanagementTest extends TestCase {
	/**
	 * Test of restringing method, of class XMLmanagement.
	 * @throws javax.xml.parsers.ParserConfigurationException
	 * @throws javax.xml.transform.TransformerException
	 */
	public void testRestringing() throws ParserConfigurationException, TransformerException {
		System.out.println("restringing");
		
		int sid = 1;
		Map<Integer, List<Integer>> questionsAnswers = new HashMap<>();
		List<Integer> answers = new ArrayList<>();
		answers.add(5); answers.add(8);
		questionsAnswers.put(2, answers);
		questionsAnswers.put(4, answers);
		
		Response r = new Response();
		r.createResponse(sid, questionsAnswers);
		r.createResponse(sid+8, questionsAnswers);
		
		XMLmanagement xman = new XMLmanagement(r.getDoc());
		System.out.println(xman.restringing());
	}

	/**
	 * Test of exporting method, of class XMLmanagement.
	 * @throws javax.xml.parsers.ParserConfigurationException
	 * @throws java.io.FileNotFoundException
	 * @throws javax.xml.transform.TransformerException
	 */
	public void testExporting() throws ParserConfigurationException, FileNotFoundException, TransformerException {
		System.out.println("exporting");
		
		int sid = 1;
		Map<Integer, List<Integer>> questionsAnswers = new HashMap<>();
		List<Integer> answers = new ArrayList<>();
		answers.add(5); answers.add(8);
		questionsAnswers.put(2, answers);
		questionsAnswers.put(4, answers);
		
		Response r = new Response();
		r.createResponse(sid, questionsAnswers);
		r.createResponse(sid+8, questionsAnswers);
		
		XMLmanagement xman = new XMLmanagement(r.getDoc());
		xman.exporting("response.xml");
	}

	/**
	 * Test of importing method, of class XMLmanagement.
	 * @throws javax.xml.parsers.ParserConfigurationException
	 * @throws org.xml.sax.SAXException
	 * @throws java.io.IOException
	 * @throws javax.xml.transform.TransformerException
	 */
	public void testImporting() throws ParserConfigurationException, SAXException, IOException, TransformerException {
		System.out.println("importing");
		
		XMLmanagement xman = new XMLmanagement();
		xman.importing("response.xml");
		
		System.out.println(xman.restringing());
	}
	
}
