window.onload = function(){
    var socket = new WebSocket('ws://localhost:8080/basic/chat');
    console.log(socket);
    console.log(socket.readyState);

    socket.onopen = () => {
        console.log('WebSocket is open');
    }
    socket.onmessage = (event) => {
        console.log("message from server: ", event.data);
    }
    var message = {type: 'ENTER', roomId: '4ed17290-1f2f-fc20-df7b-855626dede16', sender:'test',  message: "Hello world!"};
    message = JSON.stringify(message);
    socket.onopen = (event) => {
        socket.send(message);
    }
    socket.onclose = () => {
        console.log("WebSocket is closed");
    }
}