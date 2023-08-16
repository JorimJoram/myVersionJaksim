var totalPage;
var nowPage = 1;
var prevButton, nextButton, pageNum;

window.onload = function(){
    totalPage = document.getElementById('payment_total_page').innerHTML;
    pageNum = document.getElementById('payment_page_num');
    nextButton = document.getElementById('payment_next_button');
    prevButton = document.getElementById('payment_prev_button');

    nextButton.addEventListener('click', nextButtonListener);
    prevButton.addEventListener('click', prevButtonListener);

    checkNextPrevButton();

    getPay(); // 여기서 데이터 받아오시면 됩니다!
}

function getPay(){
    /*!! 반드시 URL 링크 변경하시고 사용하길 바랍니다 !!*/
    /*!!! 필요시에 추가하시거나 가감하면서 진행하시면 됩니다. !!!*/
    var test = '/image/Training.jpg';
    axios.get(`/payment/api/${nowPage}`)
        .then(response => {
            html = '';
            var container = document.getElementById('pay_list_tbody');
            var test = '/image/Training.jpg';
            response.data.forEach(item => {
                    html += '<tr>';
                    html += `<td><img class="payment_item_img" src=${test} alt="TrainingPic"></td>`;
                    html += `<td><span>${refundTranslate(item.refund)}</span></td>`;
                    html += `<td><span>${item.idx}</span></td>`;
                    html += `<td><span>이름</span></td>`; //트레이너 이름
                    html += `<td><span>가격</span></td>`; //가격
                    html += `<td><span>${item.c_dt}</span></td>`;
                    html += `<td><button class="jaksim_btn" onclick="window.location.href='/payment/detail/${item.tid}'"+>상세 조회</button></td>`;
                    html += '<tr>';
                });
                container.innerHTML = html;
            })
        .catch(error => {
            console.error(error);
        })
}

function refundTranslate(num){
    var result = '';
    switch(num){
        case 0:
            result = '구매 완료';
            break;
        case 1:
            result = '환불 완료';
            break;
    }
    return result;
}

function checkNextPrevButton(){
    if(totalPage == 1){
        nextButton.disabled = true;
        prevButton.disabled = true;
    }

    if(nowPage >= totalPage){
        nextButton.disabled = true;
    }else if(nowPage <= 1){
        prevButton.disabled = true;
    }else{
        nextButton.disabled = false;
        prevButton.disabled = false;
    }
}

function prevButtonListener(){
    nowPage -= 1;
    pageNum.innerHTML = nowPage;
    checkNextPrevButton();
    getPay();
}

function nextButtonListener(){
    nowPage += 1;
    pageNum.innerHTML = nowPage;
    checkNextPrevButton();
    getPay();
}
