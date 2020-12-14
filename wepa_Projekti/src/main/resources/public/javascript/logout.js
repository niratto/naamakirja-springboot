var url = contextRoot + "logout";

// Implement the functionality to retrieve a random book here
var http = new XMLHttpRequest();

function doLogout() {
    alert("wtf: " + url);
    http.open("POST", url, true);
    http.send();
}