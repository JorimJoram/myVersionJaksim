var userIdSpan, userEmailSpan, failCheckSpan;
var checkButton;
var oriEmail, userId;

window.onload = function(){
    userIdSpan = document.getElementById('find_pw_userid');
    userEmailSpan = document.getElementById('find_pw_email');
    failCheckSpan = document.getElementById('find_pw_failSpan');

    checkButton = document.getElementById('find_pw_check');
    checkButton.addEventListener('click', function(){
       checkUser();
    });
};

function checkUser(){
    axios.get(`/account/api/user-info?username=${userIdSpan.value}`)
        .then(response => {
            oriEmail = response.data['email'];
            userId = response.data['id'];
            checkEmail();
        })
        .catch(error => {
            console.error(error);
        });
}

function checkEmail(){
    if(oriEmail === userEmailSpan.value){
        sessionStorage.setItem('userEmail', oriEmail);
        sessionStorage.setItem('userId', userId)
        failCheckSpan.innerHTML = '';
        window.location.href='/find/userpw/cert';
    }else{
        failCheckSpan.innerHTML = '계정과 이메일 정보가 일치하지 않습니다.';
    }
}