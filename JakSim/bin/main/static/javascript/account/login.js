window.onload = function(){
    var urlParams = new URLSearchParams(window.location.search);
    var errorCode = urlParams.get('errorCode');

    var failSpan = document.getElementById('login_failure_span');
    failSpan.innerHTML = getErrorMessage(errorCode);
    failSpan.style.color = 'red';
}

function getErrorMessage(errorCode){
    switch(errorCode){
        case null:
            return '';
        case 'not_found':
            return '계정을 찾을 수 없습니다.';
        case 'bad_credentials':
            return '아이디와 비밀번호를 다시 확인해주세요';
        case 'account_expired':
            return '예외처리된 계정입니다.';
        default:
            return '예상하지 못한 애러인데... 고객센터로 연락 한 번 주세요;;';
    }
}