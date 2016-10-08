//var wsUri = "ws://54.68.97.161:8080/Diamodo/userChatSocket/57cc26217c7b123bfe82b1cc/bc9ae4b1-bee5-4c01-8644-8ab06871d6e6";

function initiateSocketConnection() {
	
	 var wsUri =
		 "ws://localhost:8080/diamodo-0.0.1-SNAPSHOT/userChatSocket/" +get('clientId')+  "/"+get('userToken');

	 
	websocket = new WebSocket(wsUri);
	websocket.onopen = function(evt) {
		onOpen(evt);
	};
	websocket.onmessage = function(evt) {

		console.log(evt.data);
	};
	websocket.onerror = function(evt) {
		onError(evt);
	};

}


function get(name){
	   if(name=(new RegExp('[?&]'+encodeURIComponent(name)+'=([^&]*)')).exec(location.search))
	      return decodeURIComponent(name[1]);
	}


function onOpen(evt) {

}

function onMessage(evt) {
	console.log(evt);
}

function onError(evt) {
}

function doSend(message) {
	var webSocketInboundMessage = new Object();
	webSocketInboundMessage.socketMessageType = 1;
	var data = new Object();
	data.senderId = get('clientId');
	data.destinationId = get('destinationId');
	data.messageContent = document.getElementById("textID").value;
	data.chatMessageType = 1;
	webSocketInboundMessage.data = data;
	webSocketInboundMessage = JSON.stringify(webSocketInboundMessage);
	websocket.send(webSocketInboundMessage);
}

function writeToScreen(message) {
	var pre = document.createElement("p");
	pre.style.wordWrap = "break-word";
	pre.innerHTML = message;

	output.appendChild(pre);
}