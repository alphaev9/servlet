var a = document.getElementById("submit");
var result = document.querySelector("#result");
var submit = function () {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "content");
    xhr.setRequestHeader('content-type', 'application/json');
    xhr.send(JSON.stringify({
        title: "java servlet technology",
        author: "alpha",
        press: "crc"
    }))
    xhr.onreadystatechange = function (ev) {
        if (xhr.readyState == 4) {
            if (xhr.getResponseHeader("content-type") == "text/myFormat;charset=utf-8") {
                result.innerHTML = "<p>" + xhr.responseText + "</p>"
            }
        }
    }
}
a.addEventListener("click", submit);