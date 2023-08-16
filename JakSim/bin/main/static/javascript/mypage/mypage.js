var nameChangeButton, telChangeButton;
var nameInput, telInput;

window.onload = function() {
    var deleteButton = document.getElementById('mypage_delete');
    nameChangeButton = document.getElementById('profile_name_change');
    telChangeButton = document.getElementById('profile_tel_change');
    nameInput = document.getElementById('profile_name_input');
    telInput = document.getElementById('profile_tel_input');

    reviewCheck();
    paymentCheck();

    deleteButton.addEventListener('click', deleteUser);

    var isNameChange = true;
    nameChangeButton.addEventListener('click', function(){
        if(isNameChange){
            nameChangeInput();
            isNameChange = false;
        }else{
            nameChangeResult();
            isNameChange = true;
        }
    });

    var isTelChange = true;
    telChangeButton.addEventListener('click', function(){
        if(isTelChange){
            telChangeInput();
            isTelChange = false
        }else{
            checkTel();
            isTelChange = true;
        }
    });

    var viewReviewButtons = document.getElementsByClassName('mypage_viewReview');
    Array.from(viewReviewButtons).forEach((button) => {
        button.addEventListener('click', function(event){
            var reviewId = event.target.closest('tr').querySelector('.jaksim_font td:first-child span').textContent;
            window.location.href=`/review/editReview/${reviewId}`;
        });
    });

    var viewPayButtons = document.getElementsByClassName('mypage_viewPayDetail');
    Array.from(viewPayButtons).forEach((button) => {
        button.addEventListener('click', function(event){
            var tid = event.target.closest('tr').querySelector('td:first-child span').textContent;
            console.log(tid);
            window.location.href=`/payment/detail/${tid}`; //tid로 변경바람
        });
    });
}

function paymentCheck(){
    function priceFormat(){
        var prices = document.getElementsByClassName('mypage_pay_price');
        Array.from(prices).forEach((item) => {
            item.innerHTML = item.innerHTML.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        });
    };

    function typeToString(){
        var types = document.getElementsByClassName('mypage_pay_type');
        Array.from(types).forEach((item) => {
            switch(item.innerHTML){
                case '0':
                    item.innerHTML = '[1:1]';
                    break;
                case '1':
                    item.innerHTML = '[단체]';
                    break;
            }
        })
    };

    function lengthOfTitle(){
        var titles = document.getElementsByClassName('mypage_pay_content');
        Array.from(titles).forEach((item, index) => {
            if(item.innerHTML.length > 23){
                item.innerHTML = item.innerHTML.substring(0, 23) + "...";
            }
        });
    };

    priceFormat();
    typeToString();
    lengthOfTitle();
}

function reviewCheck(){
    var reviews = document.getElementsByClassName('mypage_review_content');
    Array.from(reviews).forEach((item, index) => {
        if(item.innerHTML.length > 40){
            item.innerHTML = item.innerHTML.substring(0, 40) + "...";
        }
    });
}

function telChangeInput(){
    telInput.readOnly = false;
    telInput.focus();
    telChangeButton.textContent = '확인';
}

function telChangeResult(){
    telChangeButton.textContent = '수정';
    telInput.readOnly = true;

    axios.put('/account/api/change-tel', {tel: telInput.value})
        .then(response => {
            if(response.data > 0){
                alert('전화번호가 정상적으로 변경되었습니다.');
                window.location.reload();
            }
        })
        .catch(error => {
            console.error(error);
        });
}

function nameChangeResult(){
    nameChangeButton.textContent = '수정';
    nameInput.readOnly = true;

    axios.put('/account/api/change-name', {name: nameInput.value})
        .then(response => {
            if(response.data > 0){
                alert('이름이 정상적으로 변경되었습니다.');
                window.location.reload();
            }else{
                alert('이름이 변경되지 않았습니다. 다시 시도해주세요');
            }

        })
        .catch(error => {
            console.error(error);
        });
}

function checkTel(){
    axios.get(`/account/api/verify-tel?tel=${telInput.value}`)
            .then(response => {
                if(response.data === true){
                    telChangeResult();
                }else{
                    alert('이미 존재하는 전화번호입니다.');
                }
            })
            .catch(error => {
                console.error(error);
            });
}

function nameChangeInput(){
    nameInput.readOnly = false;
    nameInput.focus();
    nameChangeButton.textContent = '확인';
}

var deleteUser = function(){
    var data = {id : document.getElementById('navi_username').innerHTML};

    axios.delete('/account/api/delete')
        .then(response => {
            if(response.data === 1){
                alert('회원정보가 삭제되었습니다.');
                window.location.href='/logout';
            }else{
                alert('회원정보가 삭제되지 않았습니다. 다시 시도해주세요');
            }
        })
        .catch(error => {
            console.error(error);
        });
}
