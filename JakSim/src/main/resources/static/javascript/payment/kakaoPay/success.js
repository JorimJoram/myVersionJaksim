window.onload = function () {
    var btn = document.getElementById("modalCloseBtn");

    btn.addEventListener('click', function () {
        window.close();
    });

    priceFormat();
}

function priceFormat(){
    var amount = document.getElementById('amount');
    var method = document.getElementById('payment_method_type');

    amount.innerHTML = amount.innerHTML.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");

    switch (method.innerHTML) {
        case " : MONEY":
            method.innerHTML = " : 현금";
            break;
        case " : CARD":
            method.innerHTML = " : 카드";
            break;
        default :
            method.innerHTML = " : ETC";
            break;
    }
}