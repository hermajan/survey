/**
 * Implementation of the server for the web interface.
 * @type String "jsp" or "php"
 */
var implementation = "php";

/**
 * Highlights current page in menu.
 */
function activeLinks() {
	var hash = window.location.hash.replace("#", "");
	
	if(hash === "") {
		document.querySelectorAll(".nav li")[0].classList.add("active");
	}
	else {
		var navli = document.querySelectorAll(".nav li");
		for(var i=0; i<navli.length; i++) {
			navli[i].classList.remove("active");

			if(navli[i].id === hash) {
				navli[i].classList.add("active");
			}
		}
	}
	
	var nava = document.querySelectorAll(".nav a");
	for(var j=0; j<nava.length; j++) {
		nava[j].addEventListener("click", function(evt) {
			var navli = document.querySelectorAll(".nav li");
			for(var k=0; k<navli.length; k++) {
				navli[k].classList.remove("active");
			}
			evt.currentTarget.parentNode.classList.add("active");
		});
	}
}
window.addEventListener("load", activeLinks);
window.addEventListener("hashchange", activeLinks);

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

/**
 * Does a AJAX request.
 * @param {function} callback Callback function.
 * @param {string} url URL for AJAX request.
 * @param {string} responseType Type of the response.
 */
function doAjax(callback, url, responseType) {
 var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if(xhr.readyState === 4 && xhr.status === 200) {
			if(responseType === "xml") {
				callback(xhr.responseXML);
			}
			else {
				callback(xhr.responseText);
			}
		}
	};
	xhr.open("GET", url, true);
	xhr.setRequestHeader("Cache-Control", "no-cache, must-revalidate");
	xhr.setRequestHeader("Expires", "-1");
	xhr.send();
}
doAjax(changeContent, "survey.xml", "xml");
window.addEventListener("hashchange", function() { doAjax(changeContent, "survey.xml", "xml"); });
if(getParameters().length > 0) {
	doAjax(getResponse, "scriptlet."+implementation+window.location.search, "text");
}

/**
 * Changes content on page.
 * @param {xml} xml XML file.
 */
function changeContent(xml) {
	var list = document.createElement("div");
	list.className = "list-group";
	
	var surveys = xml.getElementsByTagName("survey");
	for(var i=1; i<=surveys.length; i++) {
		var a = document.createElement("a");
		a.href = "#s"+i; a.innerHTML = surveys[i-1].getElementsByTagName("title")[0].textContent;
		
		var li = document.createElement("li");
		li.id = "s"+i;
		li.appendChild(a);

		if(!document.getElementById("s"+i)) {
			document.getElementById("menu").appendChild(li);
		}
		activeLinks();
		
		var link = a.cloneNode();
		link.className = "list-group-item";
		link.innerHTML = surveys[i-1].getElementsByTagName("title")[0].textContent;
		list.appendChild(link);
	}
	
	var hash = window.location.hash.replace("#", "");
	for(var i=1; i<=surveys.length; i++) {
		var content = document.getElementById("content");
		if(hash === ("s"+i)) {
			content.innerHTML = "";
			renderQuestions(xml, i);
			break;
		}
		else {
			content.innerHTML = 
				"<h1 class='page-header'>Survey</h1>"+
				"<p>Select set of questions in the top menu or here:<\p>";
			content.appendChild(list);
		}
	}
}

/**
 * Adds response to the page.
 * @param {string} output Response.
 */
function getResponse(output) {
	document.getElementById("content").innerHTML = output;
}
