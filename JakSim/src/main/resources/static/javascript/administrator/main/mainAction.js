var currentDate, year, month, day;

window.onload = function(){
    getNowDate();

    asideProcess();
    chartProcess();
}

function chartProcess(){
    linearTypeSelect();
    areaTypeSelect();

    getAccessDataForLinear(); //draw default linear chart
    getAccessDataForArea();
}

function asideProcess(){
    getVisitCntNow();

    setYesterday();
    getVisitCntDay();
    getAccountCntDay();
    getRegistered();
    getRatioTrainer();
}

function getNowDate(){
    currentDate = new Date();
    year = currentDate.getFullYear();
    month = currentDate.getMonth()+1;
    day = currentDate.getDate();
}