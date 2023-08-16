var time;
var requestId;
var validateTime;
var timeout;
var code;

window.onload = function(){
    emailInit();
    emailProcess();

    var checkButton = document.getElementById('validate_button');
    checkButton.addEventListener('click', checkCode);

    var resendButton = document.getElementById('resend_button');
    resendButton.addEventListener('click', emailProcess);
}

function emailProcess(){
    showValidTime();
    sendMail();
}

function emailInit(){
    time = 600;
    timeSpan = document.getElementById('find_validate_time');
    emailResult = document.getElementById('find_validate_fail');
    sessionEmail = sessionStorage.getItem('userEmail');
    code = document.getElementById('find_validate_input');
}

function afterConfirm(result){
    var successSpan = document.getElementById('find_validate_success');
    if(result === false){
        successSpan.innerHTML = '';
        return ;
    }
    axios.get(`/email/api/user-info?email=${sessionStorage.getItem('userEmail')}`)
        .then(response => {
            successSpan.innerHTML = response.data['id'];
        })
        .catch(error => {
            emailResult.innerHTML = '데이터 호출에 실패했습니다.';
        })
}

function afterSend(){
    console.log('이메일 전송 완료');
}

window.addEventListener('beforeunload', function(){
    sessionStorage.clear();
    //페이지를 벗어날 때 세션에 있는 값을 제거함
})