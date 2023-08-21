window.onload = function(){
    var socket = new WebSocket('ws://localhost:8080/ws/chat');
    console.log(socket);
    console.log(socket.readyState);

    socket.onopen = () => {
        console.log('WebSocket is open');
    }
    socket.onmessage = (event) => {
        console.log("message from server: ", event.data);
    }

    var testData = {"name" : "test"};
    testData = JSON.stringify(testData);
    axios.post(`localhost:8080/chat`, testData)
        .then(response => {
            console.log(response.data);
        }).catch(error => {
            console.error(error);
        })
//    socket.onopen = (event) => {
//        socket.send(testData);
//    }
//    var message = {type: 'ENTER', roomId: '4ed17290-1f2f-fc20-df7b-855626dede16', sender:'test',  message: "Hello world!"};
//    message = JSON.stringify(message);
//    socket.onopen = (event) => {
//        socket.send(message);
//    }
    socket.onclose = () => {
        console.log("WebSocket is closed");
    }
}