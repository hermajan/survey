<%@page import="java.util.Enumeration"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page import="survey.responses.Response"%>
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
	out.print("s: " + sid + "\n");
	Map<Integer, List<Integer>> questionsAnswers = new HashMap<>();
	List<Integer> list = new ArrayList<>();
	
	Enumeration<String> parameterNames = request.getParameterNames();

	while(parameterNames.hasMoreElements()) {
      String parameterName = parameterNames.nextElement();
	  
	  int qid = 0;
	  if(parameterName.startsWith("qid")) {
		qid = Integer.parseInt(parameterName.replace("qid", ""));
		out.print("qid: " + qid + "\n");
		
		String[] parameterValues = request.getParameterValues(parameterName);
		if(questionsAnswers.containsKey(qid)) {
			list = questionsAnswers.get(qid);
		}
		else {
		  list = new ArrayList<>();
		}
		
		for(int i=0; i<parameterValues.length; i++) {
			list.add(Integer.parseInt(parameterValues[i]));
			out.println("aid: " + parameterValues[i] + "\n");
		}
		
		questionsAnswers.put(qid, list);
	  }
	}
	
	Response r = new Response();
	r.createResponse(sid, questionsAnswers);
	
	out.print("<br>");
	out.println(r.stringXML(r.getDoc()));
%>
	</body>
</html>
