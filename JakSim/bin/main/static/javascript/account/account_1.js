var accountNextButton;
var isLength = false, isCheck = false, isUsername = false, isUsernameLength = false;
var userId;
var passwordInput, confirmPasswordInput;
var pwMessage;

window.onload = function(){
    accountNextButton = document.getElementById('account_1_next');
    spanContainer = document.getElementById('account_span_container');
    inputContainer = document.getElementById('account_input_container');
    pwMessage = document.getElementById('account_pw_message');

    userId = document.getElementById('account_userid');
    userId.addEventListener('focusout', idCheck);

    pwProcess();
};

function checkIdFormat(){
    var pattern =  /^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]+$/;
    return pattern.test(userId.value);
}

function checkIdLength(){
    return userId.value.length > 4;
}

function idCheck(){
    var result = document.getElementById('account_id_result');

    checkIdLength() ? result.innerHTML = '' : result.innerHTML = '아이디는 5자 이상 작성해주세요';
    checkIdFormat() ? result.innerHTML = '' : result.innerHTML = '영문과 숫자를 포함해서 작성해주세요';

    if(checkIdLength() && checkIdFormat()){
        checkUserId(result);
        isNext();
    }

};

function checkUserId(resultSpan){
    function success(){
        resultSpan.innerHTML = '아이디가 확인되었습니다.';
        resultSpan.style.color='blue';
        isUsername = true;
        isUsernameLength = true;
        //sessionStorage.setItem('id', document.getElementById('account_userid').value); //-> 마지막에 ㅇㅇ
    }
    function fail(){
        resultSpan.style.color = 'red';
        resultSpan.innerHTML = '해당 아이디는 사용할 수 없습니다.';
        isUsername = false;
        isUsernameLength = false;
    }

    axios.get(`/account/api/verify-id?userId=${userId.value}`)
        .then(response => {
            (response.data) ? success() : fail();
        })
        .catch(error => {
            console.error(error);
        })
}

function checkPwLength(password){
    return password.length > 7;
}

function confirmPw(confirm){
    return passwordInput.value === confirm;
}

function pwProcess(){
    passwordInput = document.getElementById('account_pw_input');
    confirmPasswordInput = document.getElementById('account_confirm_pw');

    function pwLengthSuccess(){
        pwMessage.innerHTML = '';
        isLength = true;
    }
    function pwLengthFail(){
        pwMessage.innerHTML = '비밀번호는 8자 이상이어야 합니다.';
        isLength = false;
    }
    function pwConfirmFail(){
        pwMessage.innerHTML = '비밀번호가 일치하지 않습니다.';
        isCheck = false;
    }
    function pwConfirmSuccess(){
        pwMessage.innerHTML = '';
        isCheck = true;
    }

    passwordInput.addEventListener('input', function(event){
        checkPwLength(event.target.value) ? pwLengthSuccess() : pwLengthFail();
        isNext();
    });

    confirmPasswordInput.addEventListener('input', function(event){
        confirmPw(event.target.value) ? pwConfirmSuccess() : pwConfirmFail();
        isNext();
    });
}

function isNext(){
    (isCheck && isLength && isUsername && isUsernameLength) ? accountNextButton.disabled = false : accountNextButton.disabled = true;
}

function goNext(){
    sessionStorage.setItem('id', userId.value);
    sessionStorage.setItem('pw', passwordInput.value);
    window.location.href='/account/2'
}