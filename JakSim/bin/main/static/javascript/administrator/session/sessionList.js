var sessionId, username;

window.onload = function(){
    buttonInit();
}

function buttonInit(){
    var removeButtons = document.getElementsByClassName('remove_expire_button');
    Array.from(removeButtons).forEach((button) => {
        button.addEventListener('click', removeExpire);
    });
}

function removeExpire(event){
    var row = event.target.closest('tr');
    var sessionId = row.querySelector('td:nth-child(3)').innerHTML;
    console.log(sessionId);
    axios.post('/session/expire', {sessionId:sessionId})
        .then(response => {
            window.location.reload();
        })
        .catch(error => {
            console.error(error);
        });
}


