var changeEmailButton;
var emailInput;
var validateDiv;
var requestId;
var timeSpan, validateFail, codeInput;
var timeout;
var code;

window.onload = function(){
    emailInput = document.getElementById('profile_emailInput');
    var checkFileButton = document.getElementById('checkFile');
    changeEmailButton = document.getElementById('profile_change_email');
    validateDiv = document.getElementById('profile_validate_div');

    checkFileButton.addEventListener('click', changeProfileImage);
    changeEmailButton.addEventListener('click', checkEmail);
}

function showValidateTime(){
    var startTime = Date.now();
    var endTime = startTime + 180 * 1000;

    function update(){
        var currentTime = Date.now();
        var remainTime = Math.max(0, endTime - currentTime);

        var seconds = Math.floor(remainTime/1000);
        var min = Math.floor(seconds/60);
        seconds %= 60;

        min = min.toString().padStart(2, '0');
        seconds = seconds.toString().padStart(2, '0');

        timeSpan.innerHTML = `${min} : ${seconds}`;

        if(currentTime < endTime){
            requestId = requestAnimationFrame(update);
            validateFail.innerHTML = '';
        }else{
            timeout = true;
            timeSpan.innerHTML = '';
            validateFail.innerHTML = '인증코드를 다시 확인해주세요';
        }
    }
    update();
}

function stopTimer(){
    cancelAnimationFrame(requestId);
}

function checkEmail(){
    axios.get(`/email/api/verify-email?email=${emailInput.value}`)
        .then(response=>{
            if(response.data){
                document.getElementById('profile_dup_span').innerHTML = '';
                changeEmailButton.disabled = true;
                validateEmail();
            }else{
                document.getElementById('profile_dup_span').innerHTML = '이미 등록된 이메일입니다.';
            }
        })
        .catch(error =>{
            console.error(error);
        })
}

function validateEmail(){
    codeInput = document.createElement('input');
    var codeResendButton = document.createElement('button');
    var codeCheckButton = document.createElement('button');
    validateFail = document.createElement('span');
    timeSpan = document.createElement('span');
    timeout = false;

    codeInput.classList.add('profile_emailInput');
    codeInput.classList.add('jaksim_font');

    codeResendButton.classList.add('jaksim_btn');
    codeResendButton.textContent = '재전송';
    codeResendButton.style.margin = 'auto 3px';


    codeCheckButton.classList.add('jaksim_btn');
    codeCheckButton.textContent = '확인';
    codeCheckButton.style.margin = 'auto 3px';
    codeCheckButton.addEventListener('click', codeCheck);
    timeSpan.style.color = 'red';


    validateFail.style.color = 'red';

    var data = {email: emailInput.value};
    sendCode(data);

    codeResendButton.addEventListener('click', function(){
        sendCode(data);
    });

    validateDiv.appendChild(codeInput);
    validateDiv.appendChild(codeResendButton);
    validateDiv.appendChild(codeCheckButton);
    validateDiv.appendChild(timeSpan);
    validateDiv.appendChild(validateFail);
}

function sendCode(data){
    showValidateTime();
    axios.post('/email/api/send', data)
        .then(response => {
            code = response.data;
        })
        .catch(error => {
            console.error(error);
    });
}

function codeCheck(){
    if(code === codeInput.value && !timeout){
        updateEmail();
        stopTimer();
    }else{
        validateFail.innerHTML = '인증번호를 다시 확인해주세요';
    }
}

function updateEmail(){
    axios.put('/email/api/modify', {email: emailInput.value})
        .then(response => {
            if(response.data > 0){
                window.location.href='/';
                alert('이메일이 변경되었습니다.');
            }else{
                alert('이메일 변경에 실패했습니다.');
            }
        })
        .catch(error => {
            console.error(error);
        })
}

function changeProfileImage(){
    var fileInput = document.getElementById('profile_imgInput');
    var file = fileInput.files[0];

    var formData = new FormData();
    formData.append('file', file);

    if(fileInput.files.length === 0){
        alert('이미지를 다시 선택해주세요');
        return ;
    }

    var ImageType = ['.jpg', '.jpeg', '.png', '.gif', '.bmp'];
    var fileExtension = fileInput.files[0].name.substring(fileInput.files[0].name.lastIndexOf('.')).toLowerCase();

    if(!ImageType.includes(fileExtension)){
        alert('지원하지 않는 형식입니다\n.jpg .jpeg .png .gif .bmp 파일만 업로드 가능합니다.');
        return ;
    }ㄴㄴ

    axios.put('/account/api/change-image', formData, {headers: {'Content-Type' : 'multipart/form-data'}})
        .then(response => {
            if(response.data){
                alert('프로필 이미지가 변경되었습니다.');
                window.location.href='/mypage/main';
            }
        })
        .catch(error => {
            console.error(error);
        })
}

function readURL(input){
    if(input.files && input.files[0]){
        var reader = new FileReader();
        reader.onload = function(e){
            document.getElementById('profile_preview').src = e.target.result;
        };
        reader.readAsDataURL(input.files[0]);
    }else{
        document.getElementById('profile_preview').src = '';
    }
}