var url = contextRoot + "tasks"

// Implement the functionality to retrieve a random book here
var http = new XMLHttpRequest()

function submitform() {
    var pwd = document.getElementById("password").value
    var cpwd = document.getElementById("confirmPassword").value

    if (pwd === cpwd) {
        return true;
    } else {
        alert("Passwords must match!")
        returnToPreviousPage();
        return false;
    }
}


