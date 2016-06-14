<?php 
namespace Survey;

use DOMDocument;
use DOMText;

/**
 * Management of the response.
 */
class Response {
	private $doc;

	public function getDoc() {
		return $this->doc;
	}
	
	public function setDoc($doc) {
		$this->doc = $doc;
	}

	public function __construct() {
		$this->doc = new DOMDocument("1.0", "UTF-8");
		$this->doc->formatOutput = true;
		$this->doc->preserveWhiteSpace = true;
	}
	
	/**
	 * Creates XML with response.
	 * @param sid ID of survey.
	 * @param questionsAnswers Map of questions and answers.
	 */
	public function createResponse($sid, $questionsAnswers) {
		if($this->doc->getElementsByTagName("responses")->length == 0) {
			$responses = $this->doc->createElement("responses");
			$responses->setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			$responses->setAttribute("xsi:noNamespaceSchemaLocation", "response.xsd");
		}
		else {
			$responses = $this->doc->getElementsByTagName("responses")->item(0);
		}
		
		$response = $this->doc->createElement("response");
		
		$surveyId = $this->doc->createElement("surveyId");
		$surveyId->appendChild(new DOMText($sid));
		$response->appendChild($surveyId);
		
		$questions = $this->doc->createElement("questions");

		for($qid=1; $qid<=sizeof($questionsAnswers); $qid++) {
			if(array_key_exists($qid, $questionsAnswers)) {
				$question = $this->doc->createElement("question");
				$questionId = $this->doc->createElement("questionId");
				$questionId->appendChild(new DOMText($qid));
				$question->appendChild($questionId);

				$answers = $this->doc->createElement("answers");
				for($aid=0; $aid<=sizeof($questionsAnswers[$qid]); $aid++) {
					if(array_key_exists($aid, $questionsAnswers[$qid])) {
						$answerId = $this->doc->createElement("answerId");
						$answerId->appendChild(new DOMText($questionsAnswers[$qid][$aid]));
						$answers->appendChild($answerId);
					}
				}
				$question->appendChild($answers);
				$questions->appendChild($question);
			}
		}

		$response->appendChild($questions);
		$responses->appendChild($response);
		
		if($this->doc->getElementsByTagName("responses")->length == 0) {
			$this->doc->appendChild($responses);
		}
	}
}
?>
