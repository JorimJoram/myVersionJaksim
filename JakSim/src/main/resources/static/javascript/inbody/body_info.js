var data = [];
var selectOption, inBodyChart;
var totalPage;
var page;
var nextButton, prevButton;
var canvas;

window.onload = function(){
    totalPage = document.getElementById('inbody_totalPage').innerHTML;
    pageNumber = document.getElementById('inbody_pagenumber');
    canvas = document.getElementById('inbodyChart').getContext('2d');
    page = parseInt(pageNumber.innerHTML);

    getChart('weight');
    getData();

    selectOption = document.getElementById('inbody_selector');
    selectOption.addEventListener('change', (event) => {
        var selectedValue = event.target.value;
        getChart(selectedValue);
    });

    nextButton = document.getElementById('inbody_nextbutton');
    prevButton = document.getElementById('inbody_prevbutton');
    checkNextPrevButton();
}

function createData(){
    var inbodyData = {
        height: document.getElementById('inbody_input_height').value,
        weight: document.getElementById('inbody_input_weight').value,
        score: document.getElementById('inbody_input_score').value,
        fat: document.getElementById('inbody_input_fat').value,
        muscle: document.getElementById('inbody_input_muscle').value
    };

    if(inbodyData['height'] === '' || inbodyData['weight'] === ''){
        alert('신장과 체중은 반드시 작성해주세요');
        return ;
    }

    axios.post('/inbody/api/create', inbodyData)
        .then(response => {
            if(response.data > 0){
                alert('데이터가 추가되었습니다.');
                window.location.reload();
            }
        })
        .catch(error => {
            console.error(error);
        })
}

function checkNextPrevButton(){
    nextButton.disabled = (totalPage <= 1) || (page >= totalPage);
    prevButton.disabled = (totalPage <= 1) || (page <= 1);
}

function prevButtonListener(){
    page-=1;
    pageNumber.innerHTML = page;
    getData();
    checkNextPrevButton();
}

function nextButtonListener(){
    page+=1;
    pageNumber.innerHTML = page;
    getData();
    checkNextPrevButton();
}

function getData(){
    axios.get(`/inbody/api/${page}`)
        .then(response => {
            var html = '';
            var tbody = document.getElementById('table_tbody');
            response.data.forEach(item => {
                html += '<tr style="margin-top: 10px;">';
                html += '<td style="font-size:12px;" id="in_id_'+item.id+'">' + item.id + '</td>';
                html += '<td>' + item.height + '</td>';
                html += '<td>' + item.weight + '</td>';
                html += '<td>' + item.score + '</td>';
                html += '<td>' + item.fat + '</td>';
                html += '<td>' + item.muscle + '</td>';
                html += '<td>' + item.c_dt + '</td>';
                html += '<td>' + '<button class="delete_button" style="border: none; border-radius:50%; color:white; background-color:red;"> - </button>' + '</td>';
                html += '</tr>';
            });
            getBMIWeight(response.data[0].height);
            tbody.innerHTML = html;

            var delButtons = document.getElementsByClassName('delete_button');
            Array.from(delButtons).forEach((button) => {
               button.addEventListener('click', deleteData);
            });
        })
        .catch(error => {
            console.error(error);
        })
}

function getBMIWeight(height){
    //18.5 ~ 23 -> 정상 23.1 ~ 25 과체중 25 ~ 비만
    height /= 100;
    BMIArr = new Array((height*height) * 18, (height*height) * 23, (height*height) * 25).map(item => (item.toFixed(2)));
    console.log(BMIArr);
    document.getElementById('inbody_bmi_1').innerHTML = BMIArr[0];
    document.getElementById('inbody_bmi_2').innerHTML = BMIArr[1];
    document.getElementById('inbody_bmi_3').innerHTML = BMIArr[2];

}

function deleteData(event){
    var row = event.target.closest('tr');
    var tdId = row.querySelector('td:first-child').id;
    var itemId = document.getElementById(tdId).textContent;

    axios.delete(`/inbody/api/remove/${itemId}`)
        .then(response => {
            if(response.data > 0){
                alert('데이터가 삭제되었습니다.');
                window.location.reload();
            }else{
                alert('데이터가 삭제되지 않았습니다.');
            }
        })
        .catch(error => {
            console.error(error);
        })
}

function getChart(select){
    axios.get('/inbody/api/chart-data')
        .then(response => {
            if(response.data.length === 0){ response.data = [{ id: 0, height: 0, weight: 0, score: 0, fat: 0, muscle: 0, c_dt: Date.now()}]; }
            dataProcess(response.data, response.data.map(data => data['c_dt']), select);
        })
        .catch(error => {
            console.error(error);
        });
}

function dataProcess(dataList, label, select) {
    if(inBodyChart){inBodyChart.destroy();}
    var chartType = 'line';
    var startData = dataList.map(data => data[select]);
    var option = {scales: { }};

    //0인 데이터 및 레이블 제거
    startData = startData.filter((value, index) => { return (value !== 0) ? true : false; });
    label = label.filter((_, index) => dataList.map(data => data[select])[index] !== 0);
    option.scales[select] = {type: 'linear', position: 'left', beginAtZero: false};

    if(select === 'weight'){
        var bmiData = startData.map(function(weight, index){
            var height = (dataList.map(data => data['height'])[index]) / 100;
            return parseFloat((weight / (height * height)).toFixed(2));
        });
        bmiData = {label: 'bmi', yAxisID: 'bmi', data: bmiData, borderWidth: 2, tension: 0.4};
        option.scales['bmi'] = {type: 'linear', position: 'right', beginAtZero: false};
    }

    startData = {label: select, yAxisID: select, data: startData, borderWidth: 2, tension:0.4};

    inBodyChart = (bmiData === undefined) ? showChart([startData], label, option, chartType, canvas) : showChart([startData, bmiData], label, option, chartType, canvas);
}