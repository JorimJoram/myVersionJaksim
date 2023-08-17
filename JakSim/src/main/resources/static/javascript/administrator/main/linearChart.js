var linearChart, newLinearChart;

function linearTypeSelect(){
    var radioButtons = document.querySelectorAll('input[name="linear-range"]');
    radioButtons.forEach((button) => {
        button.addEventListener('change', () => {
            if(button.checked){
                switchLinearType(button.value);
            }
        });
    });
}

function dateCalculator(date){
    var timeStamp = currentDate.getTime();
    var oneDayMilSeconds = date * 24 * 60 * 60 * 1000;
    var beforeDayTimestamp = timeStamp - oneDayMilSeconds;
    var target = new Date(beforeDayTimestamp);

    return `${target.getFullYear()}-${target.getMonth()+1}-${target.getDate()}`;
}

function switchLinearType(linearType){
    var start = '';
    var end = '';
    switch(linearType){
        case 'tweek':
            start = `${dateCalculator(14)}`;
            break;
        case 'amon':
            start = `${dateCalculator(30)}`;
            break;
        case 'tmon':
            start = `${dateCalculator(90)}`;
            break;
        default:
            console.log('뭘 고른거여?');
    }
    getAccessDataForLinear(start, end);
}

function isStartUndefined(item){
    if(item === undefined)
        item = `${dateCalculator(14)}`;
    return item;
}

function isEndUndefined(item){
    return '';
}

function getAccessDataForLinear(start, end){
    var startDate=isStartUndefined(start);
    var endDate=isEndUndefined(end);

    axios.get(`/man/api/visit/date?start=${startDate}&end=${endDate}`)
        .then(response => {
            linearDataProcess(response.data);
        }).catch(error => {
            console.error(error);
        });
}

function avgData(list){
    return ((list.reduce((acc, cur) => acc + cur, 0))/list.length).toFixed(2);
}

function linearDataProcess(chartData){
    if(newLinearChart){newLinearChart.destroy();}
    linearChart = document.getElementById('man_main_linearChart');

    var labelList = chartData.map(data => data['date']);
    var dataList = chartData.map(data => data['amount']);
    var avg = avgData(dataList);
    var avgList = Array(dataList.length).fill(avg);

    document.getElementById('man_section_linearHead').innerHTML = `${labelList[0]} ~ ${labelList[labelList.length-1]} 일평균 로그인: ${avg}`;

    newLinearChart = showChart([{label:'접속자 수', data:dataList, borderWidth: 1, tension:0.4}, {label:'일평균', data:avgList, borderWidth: 3, pointRadius:0}],
            labelList,
            { responsive: true, maintainAspectRatio: false, aspectRatio: 3, y: { beginAtZero:false } },
            'line', linearChart);
}