var checkButton;
var checkSpan;
window.onload = function(){
    checkButton = document.getElementById('find_valid_useridCheck');
    emailInit();

    checkButton.addEventListener('click', emailAction);
}

function emailInit(){
    emailInput = document.getElementById('find_valid_email');
    emailResult = document.getElementById('find_valid_span');
}

function emailAction(){
    if(checkEmailFormat()){
        emailResult.innerHTML = '';
        isRegistered();
    }else{
        emailResult.innerHTML = '이메일 형식을 확인해주세요';
        emailResult.style.color = 'red';
    }
}

function next(){
    window.location.href='/find/userid/cert';
    sessionStorage.setItem('userEmail', emailInput.value);
}