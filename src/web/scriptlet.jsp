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
	</head>
	<body>
<%
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
	
	XMLmanagement xman = new XMLmanagement();
	String responseFile = request.getServletContext().getRealPath("/")+"\\response.xml";
	
	Response r = new Response();
	File f = new File(responseFile);
	if(f.exists() && !f.isDirectory()) { 
		xman.importing(responseFile);
		r.setDoc(xman.getXml());
	}
	r.createResponse(sid, questionsAnswers);
	
	xman.setXml(r.getDoc());
	xman.exporting(responseFile);
	
	responseFile = responseFile.replace("\\\\", "\\");
%>

<br>
<div class="alert alert-success" role="alert">
	Response to the survey #<%= sid %> was saved to 
	<a href="<%= responseFile %>" class="alert-link"><%= responseFile %></a>.
</div>

<a href="index.html">&lt; go back</a>
	</body>
</html>
