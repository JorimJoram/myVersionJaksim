window.onload = function(){
    initRefund();
}

function initRefund(){
    ori_refunds = document.getElementsByClassName('info_refund');
    Array.from(ori_refunds).forEach((refund) => {
        switch(refund.innerHTML){
            case '0':
                refund.innerHTML = '구매완료';
                break;
            case '1':
                refund.innerHTML = '환불처리';
                break;
        }
    });
}