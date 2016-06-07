<%@page import="org.w3c.dom.Element"%>
<%@page import="org.w3c.dom.Document"%>
<%@page import="java.io.File"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page import="survey.Response"%>
<%@page import="survey.XMLmanagement"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Scriplet</title>
		<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
		<link rel="stylesheet" href="style.css">
	</head>
	<body>
<%
	// getting data
	int sid = Integer.parseInt(request.getParameter("s"));
	Map<Integer, List<Integer>> questionsAnswers = new HashMap<>();
	List<Integer> list = new ArrayList<>();
	
	Enumeration<String> parameterNames = request.getParameterNames();

	while(parameterNames.hasMoreElements()) {
      String parameterName = parameterNames.nextElement();
	  
	  int qid = 0;
	  if(parameterName.startsWith("qid")) {
		qid = Integer.parseInt(parameterName.replace("qid", ""));
		
		String[] parameterValues = request.getParameterValues(parameterName);
		if(questionsAnswers.containsKey(qid)) {
			list = questionsAnswers.get(qid);
		}
		else {
		  list = new ArrayList<>();
		}
		
		for(int i=0; i<parameterValues.length; i++) {
			list.add(Integer.parseInt(parameterValues[i]));
		}
		
		questionsAnswers.put(qid, list);
	  }
	}
	
	// exporting to the file
	XMLmanagement xman = new XMLmanagement();
	String responseFile = request.getServletContext().getRealPath("/")+"\\responses.xml";
	
	Response r = new Response();
	File f = new File(responseFile);
	if(f.exists() && !f.isDirectory()) { 
		xman.importing(responseFile);
		r.setDoc(xman.getXml());
	}
	r.createResponse(sid, questionsAnswers);
	
	xman.setXml(r.getDoc());
	xman.exporting(responseFile);
	
	// handling of the output
	responseFile = responseFile.replace("\\\\", "\\");
	xman = new XMLmanagement();
	xman.importing(request.getServletContext().getRealPath("/")+"\\survey.xml");
	Document doc = xman.getXml();
	Element surveyElement = (Element)doc.getElementsByTagName("survey").item(sid-1);
	String surveyTitle = surveyElement.getElementsByTagName("title").item(0).getTextContent();
%>

<br>
<div class="alert alert-success" role="alert">
	Responses of the survey <%= surveyTitle %> were saved to the 
	<a href="<%= responseFile %>" class="alert-link"><%= responseFile %></a>.
</div>

<nav>
  <ul class="pager">
    <li class="previous"><a href="index.html"><span aria-hidden="true">&larr;</span> go back</a></li>
  </ul>
</nav>
	</body>
</html>
