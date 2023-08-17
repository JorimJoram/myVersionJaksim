var areaChart, newAreaChart;
var chart;

function areaTypeSelect(){
    var radioButtons = document.querySelectorAll('input[name="area-range"]');
    radioButtons.forEach((button) => {
        button.addEventListener('change', () => {
            if(button.checked){
                switchAreaType(button.value);
            }
        });
    });
}

function switchAreaType(areaType){
    var start = '';
    var end = '';

    switch(areaType){
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
    getAccessDataForArea(start, end);
}

function getAccessDataForArea(start, end){
    var startDate = isStartUndefined(start);
    var endDate = isEndUndefined(end);

    axios.get(`/man/api/visit/date?start=${startDate}&end=${endDate}`)
        .then(response => {
            areaDataProcess(response.data);
        }).catch(error => {
            console.error(error);
        })
}

function areaDataProcess(chartData){
    if(newAreaChart){newAreaChart.destroy();}
    areaChart = document.getElementById('man_main_areaChart');

    var labelList = chartData.map(data => data['date']);
    var dataList = chartData.map(data => data['amount']);
    var cumulativeSum = dataList.reduce((acc, cur) => {
        (acc.length > 0) ? acc.push(acc[acc.length-1]+cur) : acc.push(cur);
        return acc;
    }, []);

    var regressionGradient = (cumulativeSum[labelList.length -1] - cumulativeSum[0]) / (labelList.length-1);
    var regressionData = [...Array(labelList.length)].map((v, i) => (regressionGradient*(i)) + cumulativeSum[0]);

    document.getElementById('man_section_areaHead').innerHTML = `${labelList[0]} ~ ${labelList[labelList.length-1]} 증가분: ${regressionGradient.toFixed(2)}`;

    cumulativeSum = { label: '접속자 누적 수', data: cumulativeSum, borderWidth: 1, fill: true }
    regressionData = { label: '증가 기울기', data: regressionData, border: 2, pointRadius: 0 }

    newAreaChart = showChart([cumulativeSum, regressionData], labelList, { responsive: true, maintainAspectRatio: false, aspectRatio: 3 },
                'line', areaChart);
}