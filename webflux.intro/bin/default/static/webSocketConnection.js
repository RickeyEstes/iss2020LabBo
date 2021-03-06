/*
--------------------------------------------------
webSocketConnection.js
--------------------------------------------------
*/
var webSocket;
var host =  location.host;
var url  = "ws://"+host+"/demoflux";
//alert(url);

function logMessage(msg) {
    document.getElementById("result").appendChild(document.createTextNode(msg + "\n"));
}

function openWebSocket() {
    webSocket = new WebSocket( url );

    webSocket.onopen = function() { logMessage("Opened the Websocket Connection!"); };
    
    webSocket.onclose = function() { logMessage("Websocket Connection Closed !");};

    webSocket.onmessage = function(event) { logMessage("Result: " + event.data); }
}

function isOpen(ws) { return ws.readyState === ws.OPEN }

function sendString() {
    var input = document.getElementById("input").value;
    if (!isOpen(webSocket)){
    	console.log("socket closed");
     }else  webSocket.send(input);
}