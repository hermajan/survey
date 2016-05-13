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
 * Does a AJAX request for obtaining questions.
 */
function doAjax() {
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if(xhr.readyState === 4 && xhr.status === 200) {
			changeContent(xhr.responseXML);
		}
	};
	xhr.open("GET", "../xml/survey.xml", true);
	xhr.send();
}
window.addEventListener("load", doAjax);
window.addEventListener("hashchange", doAjax);

/**
 * Changes content on page.
 * @param {xml} xml XML file.
 */
function changeContent(xml) {
	var surveys = xml.getElementsByTagName("survey");
	for(var i=1; i<=surveys.length; i++) {
		var a = document.createElement("a");
		a.href = "#s"+i; a.innerHTML = "Survey #"+i;
		
		var li = document.createElement("li");
		li.id = "s"+i;
		li.appendChild(a);
		
		if(!document.getElementById("s"+i)) {
			document.getElementById("menu").appendChild(li);
		}
		
		activeLinks();
	}
	
	var hash = window.location.hash.replace("#", "");
	for(var i=1; i<=surveys.length; i++) {
		if(hash === ("s"+i)) {
			document.getElementById("content").innerHTML = "";
			renderQuestions(xml, i);
			break;
		}
		else {
			document.getElementById("content").innerHTML = 
				"<h1 class='page-header'>Survey</h1>"+
				"<p>Select set of questions in the top menu.<\p>";
		}
	}
}
