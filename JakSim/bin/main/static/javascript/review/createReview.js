var starSelector, reviewContent;
window.onload = function(){
    getElements();
}

function getElements(){
    starSelector = document.getElementById('star');
    reviewContent = document.getElementById('reviewContent');
}

function saveListener(){
    data = {
        star : starSelector.value,
        content: reviewContent.value
    };
    console.log(data);

    var currentURL = window.location.href;
    var pathValue = new URL(currentURL).pathname;
    var tid = pathValue.split("/").pop()
    axios.post(`/review/api/register/${tid}`, data)
        .then(response => {
            if(response.data > 0){
                alert('리뷰등록이 완료되었습니다.');
                window.location.href=`/payment/detail/${tid}`;
            }
        }).catch(error => {
            console.error(error);
        })
}
