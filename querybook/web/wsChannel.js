(function () {
        var ws = new WebSocket("ws://localhost:8080/test_war_exploded//wsChannel");
        ws.onmessage = function (ev) {
            var data = JSON.parse(ev.data);
            // console.log(message.messageType);
            var notificationText = "you have new message";
            console.log(data.message);
            notification(notificationText);
            handleResult(data.message)

        }
        ws.onopen = function () {
            console.log("websocket is opened")
        }
    }
)();

function handleResult(text) {
    let p = document.createElement("p");
    p.innerText=text;
    let body = document.querySelector("body");
    body.appendChild(p)
}

/*function handleResult(books) {
    let table = document.querySelector("tbody")[0];

    for (var i = 0; i < books.length; i++) {
        let tr = document.createElement('tr');
        tr.innerHTML = "<td>" + books[i].title + "<td>" +
            "<td>" + books[i].press + "<td>";
        tr.className = "warn"
        table.appendChild(tr)
    }
}*/


function notification(notificationText) {
    if (Notification.permission = "granted") {
        var notification = new Notification(notificationText);
    } else {
        if (Notification.permission !== 'denied') {
            Notification.requestPermission(
                function (permission) {
                    // 如果用户同意，就可以向他们发送通知
                    if (permission === "granted") {
                        var notification = new Notification(notificationText);
                    }
                })
        }
    }
}