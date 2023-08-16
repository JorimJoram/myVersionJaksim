var checkButton;
var requestId;
var userId;
var parentDiv;
var pwInput, confirmInput;

window.onload = function(){
    emailInit();
    emailProcess();

    checkButton = document.getElementById('find_pw_validButton');
    checkButton.addEventListener('click', checkCode);
    resendButton = document.getElementById('find_pw_resend_button');
    resendButton.addEventListener('click', emailProcess);
};

function emailProcess(){
    showValidTime();
    sendMail();
}

function emailInit(){
    time = 600;
    timeSpan = document.getElementById('find_pw_validTime');
    emailResult =  document.getElementById('find_pw_validFail');
    sessionEmail = sessionStorage.getItem('userEmail');
    code = document.getElementById('find_pw_validInput');

    userId = sessionStorage.getItem('userId');
    parentDiv = document.getElementById('find_pw_changePwDiv');
}

function passwordChange(){
    var html = '';
    html += '<div><span class="jaksim_font">비밀번호 변경</span></div>';
    html += '<input id="pw_find_pwInput" class="email_input" type="password" placeholder="비밀번호를 입력해주세요">';
    html += '<input id="pw_find_confirm" class="email_input" type="password" placeholder="비밀번호 확인바랍니다.">';
    html += '<button class="jaksim_btn" onclick="changeProcess()">비밀번호 변경</button>';
    html += '<div><p id="pw_find_fail"></p></div>';

    parentDiv.innerHTML = html;

    failMessage = document.getElementById('pw_find_fail');

    pwInput = document.getElementById('pw_find_pwInput');
    confirmInput = document.getElementById('pw_find_confirm');
}

function lengthCheck(){
    return pwInput.value.length > 7;
}

function confirmPassword(){
    return pwInput.value === confirmInput.value;
}

function changeProcess(){
    if(lengthCheck() && confirmPassword()){
        storePassword();
    }else if(!lengthCheck()){
        failMessage.innerHTML = '8자 이상 작성해주세요';
    }else{
        failMessage.innerHTML = '비밀번호가 일치하지 않습니다.';
    }
}

function storePassword(){
    function success(){
        alert('비밀번호가 변경되었습니다.');
        window.location.href = '/login';
    }

    axios.put('/account/api/change-pw', {id: userId, pw: pwInput.value})
        .then(response => {
            (response.data > 0) ? success() : alert('다시 시도해주세요');
        })
        .catch(error => {
            console.error(error);
        });
}

function afterSend(){
    console.log('이메일 전송 완료');
}

function afterConfirm(result){
    if(result){
        passwordChange();
    }
}

