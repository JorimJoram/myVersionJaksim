var isEmail = false, isPhone = false;
var emailCheckButton;
var phoneInput;


window.onload = function(){
    emailCheckButton = document.getElementById('account_email_button');
    var phoneCheckButton = document.getElementById('account_phone_button');
    nextButton = document.getElementById('account_3_next');

    nextButton.addEventListener('click', nextPage);
    phoneCheckButton.addEventListener('click', checkPhone);

    emailInit();

    emailCheckButton.addEventListener('click', function(){
        if(checkEmailFormat()){
            isDuplicated();
        }
    });
};

function afterSend(){
    createTags();
}

function emailInit(){
    emailInput = document.getElementById('account_email');
    emailDupSpan = document.getElementById('account_check_dupmail');
    parentDiv = document.getElementById('account_code_container');
    time = 180;
}

function nextPage(){
    sessionStorage.setItem('email', document.getElementById('account_email').value);
    sessionStorage.setItem('tel', document.getElementById('account_tel').value);
    window.location.href='/account/4';
}

function afterConfirm(emailResult){
    (emailResult) && (!timeout) ? isEmail = true : isEmail = false;
    console.log(emailResult);
    console.log(emailResult && !timeout);
    isEmail && isPhone ? nextButton.disabled = false : nextButton.disabled = true;
}

function checkPhoneFormat(){
    var pattern = /^[0-9]+$/;
    return pattern.test(phoneInput.value);
}

function checkPhoneLength(){
    return phoneInput.value.length > 6;
}

function checkPhone(){
    phoneInput = document.getElementById('account_tel');
    phoneSpan = document.getElementById('account_phone_span');
    phoneSpan.style.color = 'red';
    var data = {tel : phoneInput.value};

    checkPhoneFormat() ? phoneSpan.innerHTML = '' : phoneSpan.innerHTML = '숫자만 입력해주세요';
    checkPhoneLength() ? phoneSpan.innerHTML = '' : phoneSpan.innerHTML = '전화번호를 다시 확인해주세요';

    function success(){
        isPhone = true;
        phoneSpan.innerHTML = '확인 되었습니다.';
        phoneSpan.style.color='blue';
    }
    function fail(){
        isPhone = false;
        phoneSpan.innerHTML = '등록된 번호 입니다.';
    }

    if(checkPhoneLength() && checkPhoneFormat()){
        axios.get(`/account/api/verify-tel?tel=${phoneInput.value}`)
            .then(response => {
                response.data ? success() : fail();
                isEmail && isPhone ? nextButton.disabled = false : nextButton.disabled = true;
            })
            .catch(error => {console.error(error)})
    }
}