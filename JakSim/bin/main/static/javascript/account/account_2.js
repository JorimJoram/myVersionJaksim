window.onload = function(){
    var nextButton = document.getElementById('account_2_next');
    nextButton.addEventListener('click', storageDataSession);
}

function validateKorean(input){
    var pattern = /^[ㄱ-ㅎㅏ-ㅣ가-힣]+$/;
    if(!pattern.test(input.value)){
        input.value = '';
    }
}

function storageDataSession(){
    var name = document.getElementById('account_name').value;
    var birth = document.getElementById('account_birth').value;
    var gender = selectGender();
    
    sessionStorage.setItem('name', name);
    sessionStorage.setItem('birth', birth);
    sessionStorage.setItem('gender', gender);

    (name !== '' && birth !== '' && gender !== null) ? window.location.href='/account/3' : alert('입력을 모두 완료해주세요');
}

function selectGender(){
    var maleRadioButton = document.getElementById('account_gender_male');
    return maleRadioButton.checked ? 0 : 1;
}