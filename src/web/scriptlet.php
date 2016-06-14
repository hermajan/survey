<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Scriplet</title>
		<link rel="shortcut icon" href="favicon.ico">
		<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
		<link rel="stylesheet" href="style.css">
	</head>
	<body>
<?php
	// getting data
	$sid = $_GET['s'];
	
	$questionsAnswers = array(array());
	
	$parameterNames = explode("&", $_SERVER["QUERY_STRING"]);	
	foreach($parameterNames as $parameterName) {
	  $qid = 0;
	  if(preg_match('#^qid#', $parameterName) === 1) {
		$parameter = explode("=", $parameterName);
		$qid = str_replace("qid", "", $parameter[0]);

		$parameterValue = $parameter[1];
		if(!array_key_exists($qid, $questionsAnswers)) {
		  $questionsAnswers[$qid] = array();
		}
		array_push($questionsAnswers[$qid], $parameterValue);
	  }
	}
	
	// exporting to the file
	$responseFile = "responses.xml";

	require_once "Response.php";
	$r = new Survey\Response();
	$doc = new DOMDocument("1.0", "UTF-8");
	$doc->formatOutput = true;
	$doc->preserveWhiteSpace = true;
	if(file_exists($responseFile) && !is_dir($responseFile)) {
		$doc->load($responseFile);
		$r->setDoc($doc);
	}
	$r->createResponse($sid, $questionsAnswers);
	
	$r->getDoc()->save($responseFile);
	
	// handling of the output
	str_replace("\\\\", "\\", $responseFile);
	$url = "https://".$_SERVER["SERVER_NAME"].$_SERVER["PHP_SELF"];
	$responseFile = str_replace("scriptlet.php", "", $url).$responseFile;

	$doc->load("survey.xml");
	$surveyElement = $doc->getElementsByTagName("survey")->item($sid-1);
	$surveyTitle = $surveyElement->getElementsByTagName("title")->item(0)->nodeValue;
?>

<br>
<div class="alert alert-success" role="alert">
	Responses of the survey <?php echo $surveyTitle; ?> were saved to the 
	<a href="<?php echo $responseFile; ?>" class="alert-link"><?php echo $responseFile; ?></a>.
</div>

<nav>
  <ul class="pager">
	<li class="previous"><a href="index.html"><span aria-hidden="true">&larr;</span> go back</a></li>
  </ul>
</nav>
	</body>
</html>
