var navTime;
var requestId;

function showSessionValidTime(){
    navTime = document.getElementById('nav_time');
    if(navTime === null)
        return ;
    axios.get('/mypage/api/session-time')
        .then(response => {
            countDown(response.data, navTime);
        })
        .catch(error => {
            console.error(error);
        });
}

function countDown(time, navTime){
    var startTime = Date.now();
    var endTime = startTime + time * 1000;

    function update(){
        var currentTime = Date.now();
        var remainTime = Math.max(0, endTime - currentTime);
        var seconds = Math.floor(remainTime/1000);

        var min = Math.floor(seconds/60);
        seconds = seconds%60;
        min = min.toString().padStart(2, '0');
        seconds = seconds.toString().padStart(2, '0');

        navTime.innerHTML = `${min}:${seconds}`;

        if(currentTime < endTime){
            requestId = requestAnimationFrame(update);
        }else{
            window.location.href='/logout';
        }
    }

    update();
}

function stopTimer(){
    cancelAnimationFrame(requestId);
}

function numFormat(intData){
    return intData.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

