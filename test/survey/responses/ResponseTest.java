package survey.responses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.junit.Test;
import static survey.responses.Response.printXML;

/**
 * Tests for the Response class.
 */
public class ResponseTest {
	/**
	 * Test of printXML method, of class Response.
	 * @throws javax.xml.parsers.ParserConfigurationException
	 * @throws javax.xml.transform.TransformerException
	 */
	@Test
	public void testPrintXML() throws ParserConfigurationException, TransformerException {
		Response r = new Response();
		
		int sid = 1;
		Map<Integer, List<Integer>> questionsAnswers = new HashMap<>();
		List<Integer> answers = new ArrayList<>();
		answers.add(5); answers.add(8);
		questionsAnswers.put(2, answers);
		questionsAnswers.put(4, answers);
		
		r.createResponse(sid, questionsAnswers);
		r.createResponse(sid+8, questionsAnswers);
		printXML(r.getDoc());
	}
}
