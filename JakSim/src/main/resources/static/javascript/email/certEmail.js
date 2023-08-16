var emailInput, sessionEmail; //이메일 입력한 곳
var emailDupSpan; //중복 여부 나타내주는 span
var emailCheckButton; //'인증' 버튼
var answerCode; // 발송했던 '인증번호'
var parentDiv; // 생성할 태그들 넣을 div
var timeout, time, timeSpan; //타임아웃 여부, 시간값, 화면에 나타낼 시간값
var emailResult;
var code; // 사용자가 작성한 인증번호
var requestId;

function checkEmailFormat(){
    var pattern = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
    return pattern.test(emailInput.value);
}

function isRegistered(){
    axios.get(`/email/api/verify-email?email=${emailInput.value}`)
        .then(response => {
            if(!response.data){
                sessionStorage.setItem('userEmail', emailInput.value);
                next();
            }else{
                emailResult.innerHTML = '등록되지 않은 이메일입니다.';
                emailResult.style.color = 'red';
            }
        });
}

function isDuplicated(){
    function success(){
        emailDupSpan.innerHTML = '';
        sendMail();
    };
    function fail(){
        emailDupSpan.innerHTML = '이미 등록된 이메일입니다.'
        emailDupSpan.style.color = 'red';
    };

    axios.get(`/email/api/verify-email?email=${emailInput.value}`)
        .then(response => {
            (response.data) ? success() : fail();
        })
        .catch(error => {
            console.error(error);
        });
}

function sendMail(){
    data = ((emailInput === undefined) ? {email: sessionEmail} : {email:emailInput.value})
    alert('인증번호를 전송했습니다.');
    axios.post('/email/api/send', data)
        .then(response => {
            answerCode = response.data;
            afterSend();
        })
        .catch(error => {
            console.error(error);
        });
}

function createTags(){
    var html = '';
    html += '<input id="code_input" class="email_input" >';
    html += '<button class="jaksim_btn" onclick="sendMail()">재전송</button>';
    html += '<button class="jaksim_btn" onclick="checkCode()" style="margin: 10px;">확인</button>';
    html += '<span class="email_time_font" id="timeSpan"></span>';
    html += '<div><p id="email_result"></div>';
    parentDiv.innerHTML = html;

    timeSpan = document.getElementById('timeSpan');
    emailResult = document.getElementById('email_result');
    code = document.getElementById('code_input');

    showValidTime();
}

function showValidTime(){
    timeout = false;

    var startTime = Date.now();
    var endTime = startTime + time * 1000;

    function update(){
        var currentTime = Date.now();
        var remainTime = Math.max(0, endTime - currentTime);

        var seconds = Math.floor(remainTime / 1000);
        var min = Math.floor(seconds/60);
        seconds %= 60;

        min = min.toString().padStart(2, '0');
        seconds = seconds.toString().padStart(2, '0');

        timeSpan.innerHTML = `${min} : ${seconds}`;

        if(currentTime >= endTime){
            timeout = true;
            emailResult.innerHTML = '시간이 초과되었습니다.';
            emailResult.style.color = 'red';
        }

        requestId = requestAnimationFrame(update);

    }
    update();
}

function stopTimer(){
    cancelAnimationFrame(requestId);
}

function checkCode(){
    var result;
    function success(){
        stopTimer();
        emailResult.innerHTML = '이메일이 확인되었습니다.';
        emailResult.style.color='blue';
        result = true;
    };
    function fail(){
        emailResult.innerHTML = '인증번호를 다시 확인해주세요';
        emailResult.style.color = 'red';
        result = false;
    };

    (answerCode === code.value && !timeout) ? success() : fail();
    afterConfirm(result);
}