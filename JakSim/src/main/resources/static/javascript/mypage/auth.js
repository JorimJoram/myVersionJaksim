var span;

window.onload = function(){
    var pwButton = document.getElementById('pwButton');
    span = document.getElementById('mypage_failureSpan');
    pwButton.addEventListener('click', checkPassword);

    var pwInput = document.getElementById('pwInput');
    pwInput.focus();

    pwInput.addEventListener('keypress', function(event){
        if(event.key==='Enter'){
            event.preventDefault();

            pwValue = event.target.value;
            var data = {pw: pwValue};
            sendPw(data);
        }
    });
}

var checkPassword = function(){
    var data = {pw : document.getElementById('pwInput').value}
    sendPw(data);
}

function sendPw(data){
    axios.post('/mypage/api/auth', data)
        .then(response => {
            if(response.data !== ''){
                window.location.href='/mypage/main';
            }else{
                span.innerHTML = '비밀번호를 다시 확인해주세요';
                span.style.color='red';
            }
        })
        .catch(error => {
            console.error(error);
        });
}