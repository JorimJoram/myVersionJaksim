window.onload = function(){
    createUser();
}

function createUser(){
    function success(){
        sessionStorage.clear();
        localStorage.clear();
        alert('회원가입이 완료되었습니다.');
        window.location.href='/login';
    }
    function fail(){
        alert('회원가입에 실패했습니다.');
        window.history.back();
    }
    axios.post('/account/api/register', createJSON())
        .then(response => {
            response.data > 0 ? success() : fail();
        })
        .catch(error => {
            console.error(error);
        });
}

function createJSON(){
    return {
        id : sessionStorage.getItem('id'),
        pw : sessionStorage.getItem('pw'),
        name: sessionStorage.getItem('name'),
        birth: sessionStorage.getItem('birth'),
        gender: sessionStorage.getItem('gender'),
        tel : sessionStorage.getItem('tel'),
        email : sessionStorage.getItem('email')
    };
}