/**
 * Does a AJAX request for obtaining questions.
 * @param {type} param AJAX parameters.
 */
$.ajax({
	type: "GET",
	url: "test.xml",
	dataType: "xml",
	success: function(data) {
		saveAnswers($(data)[0]);
		renderQuestions($(data)[0]);
    },
	error: function() {
		alert("error");
	}
});

/**
 * Renders form with questions.
 * @param {xml} xml XML with questions.
 */
function renderQuestions(xml) {
	var form = document.createElement("form");
	form.action = "#";
	form.method="get";
	form.name = "qForm";
	
	var questions = xml.getElementsByTagName("question");
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
	document.getElementById("output").appendChild(form);
	
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
	task.id = question.id;
	//question.classList.add("question");
	
	var query = document.createElement("legend");
	query.innerHTML = question.getElementsByTagName("text")[0].innerHTML;
	task.appendChild(query);
	
	var answers = question.getElementsByTagName("answer");
	for(var i=0; i<answers.length; i++) {
		var answer = document.createElement("div");
		answer.classList.add("radio");
		
		var box = document.createElement("input");
		box.type = "radio";
		box.id = answers[i].id;
		box.name = task.id;
		box.value = answers[i].id;
		
		var label = document.createElement("label");
		label.htmlFor = answers[i].id;
		label.appendChild(box);
		label.innerHTML += answers[i].innerHTML;
		
		answer.appendChild(label);
		task.appendChild(answer);
	}
	
	return task;
}

var correctAnswers = [];
/**
 * Saves correct answers to an array for later usage.
 * @param {xml} xml XML with questions.
 */
function saveAnswers(xml) {
	var answers = xml.getElementsByTagName("answer");
	
	for(var i=0; i<answers.length; i++) {
		var answer = answers[i];
		if(answer.hasAttribute("ok") || answer.attributes.ok === "true") {
			correctAnswers.push(answer.id);
		}
	}
	//console.log(correctAnswers);
}

/**
 * Checks if given answers are correct.
 * @returns {undefined}
 */
function checkAnswers() {
	var params = getParameters();
	
	for(var i=0; i<params.length; i++) {
		if(document.getElementById(correctAnswers[i]).checked) {
			alert(document.getElementById(correctAnswers[i]).value);
			document.getElementById(correctAnswers[i]).parentNode.parentNode.classList.add("has-success");
		}
	}
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
