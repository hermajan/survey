/**
 * Renders form with questions.
 * @param {xml} xml XML data.
 * @param {string} id ID of survey.
 */
function renderQuestions(xml, id) {
	var element = document.getElementById("content");
	
	var survey = xml.querySelector("survey[sid='"+id+"']");
	
	var h1 = document.createElement("h1");
	h1.innerHTML = survey.getElementsByTagName("title")[0].innerHTML;
	element.appendChild(h1);
	
	var p = document.createElement("p");
	p.innerHTML = survey.getElementsByTagName("description")[0].innerHTML;
	element.appendChild(p);
	
	var form = document.createElement("form");
	//form.action = "";
	form.method = "get";
	form.name = "qForm";
	
	var questions = survey.getElementsByTagName("question");
	for(var i=0; i<questions.length; i++) {
		form.appendChild(getQuestion(questions[i]));
	}
	
	var button = document.createElement("button");
	button.id = "submitAnswers";
	button.className = "btn btn-default";
	//button.type = "submit";
	button.addEventListener("click", function(){ return checkAnswers(); });
	button.innerHTML = "Submit";
	
	form.appendChild(button);
	element.appendChild(form);
	
	var param = getParameters();
	if(param.length > 0) {
		for(var i=0; i<param.length; i++) {
			document.getElementById(param[i]).checked = true;
		}
	}
}

/**
 * Makes question element.
 * @param {type} question Question.
 * @returns {getQuestion.task|Element} Element with question.
 */
function getQuestion(question) {
	var task = document.createElement("fieldset");
	task.id = question.getAttribute("qid");
	//question.classList.add("question");
	
	var query = document.createElement("legend");
	query.innerHTML = question.getElementsByTagName("description")[0].innerHTML;
	task.appendChild(query);
	
	var answers = question.getElementsByTagName("answer");
	for(var i=0; i<answers.length; i++) {
		var answer = document.createElement("div");
		
		var box = document.createElement("input");
		box.id = "aid"+answers[i].getAttribute("aid");
		box.value = answers[i].getAttribute("aid");
		box.name = "qid"+task.getAttribute("id");
		
		var qtype;
		if(question.getAttribute("type") === "closed") {
			qtype = "radio";
		}
		else { 
			qtype = "checkbox";			
		}
		box.type = qtype;
		answer.classList.add(qtype);

		var label = document.createElement("label");
		label.htmlFor = answers[i].getAttribute("aid");
		label.appendChild(box);
		label.innerHTML += answers[i].innerHTML;
		
		answer.appendChild(label);
		task.appendChild(answer);
	}
	
	return task;
}

/**
 * Returns GET parameters for form.
 * @returns {Array|getParameters.parameters} Parameters.
 */
function getParameters() {
	var parameters = [];
	
	var array = window.location.search.replace("?", "").split("&");
	if(array.length > 0 && array[0] !== "") {
		for(var i=0; i<array.length; i++) {
			var value = array[i].split("=");
			parameters.push(value[1]);
		}
	}
	return parameters;
}
