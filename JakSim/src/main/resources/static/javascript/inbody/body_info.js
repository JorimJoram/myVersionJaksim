var data = [];
var selectOption, chart;
var isFirst = true;
var totalPage;
var page;
var nextButton, prevButton;

window.onload = function(){
    totalPage = document.getElementById('inbody_totalPage').innerHTML;
    pageNumber = document.getElementById('inbody_pagenumber');

    page = parseInt(pageNumber.innerHTML);

    if(isFirst){
        getChart('weight');
        getData();
        isFirst = false;
    }

    selectOption = document.getElementById('inbody_selector');
    selectOption.addEventListener('change', (event) => {
        var selectedValue = event.target.value;
        getChart(selectedValue);
    });

    nextButton = document.getElementById('inbody_nextbutton');
    nextButton.addEventListener('click', nextButtonListener);

    prevButton = document.getElementById('inbody_prevbutton');
    prevButton.addEventListener('click', prevButtonListener);

    checkNextPrevButton();

    var createButton = document.getElementById('inbody_create_button');
    createButton.addEventListener('click', createData);
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
    if(totalPage == 1){
        nextButton.disabled = true;
        prevButton.disabled = true;
    }
    if(page >= totalPage){
        nextButton.disabled = true;
    }else if(page <= 1){
        prevButton.disabled = true;
    }else{
        nextButton.disabled = false;
        prevButton.disabled = false;
    }
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

function getChart(option){
    axios.get('/inbody/api/chart-data')
        .then(response => {
            if(response.data.length === 0){
                response.data = [{
                    id: 0, height: 0, weight: 0, score: 0, fat: 0, muscle: 0, c_dt: Date.now()
                }];
            }
            showChart(response.data, response.data.map(data => data['c_dt']), option);
        })
        .catch(error => {
            console.error(error);
        });
}

function showChart(chartData, label, option){
    if(chart){chart.destroy();}

    var data = chartData.map(data => data[option]);
    for(var i=0; i<data.length; i++){
        if(data[i] === 0){
            data.splice(i, 1);
            label.splice(i, 1);
            i--;
        }
    }

    var canvas = document.getElementById('inbodyChart').getContext('2d');
    if(option === 'weight'){
        var bmiData = data.map(function(weight, index){
            var height = chartData.map(data => data['height'])[index];
            height /= 100;
            var bmi = weight / (height*height);
            return bmi.toFixed(2);
        });
        chart = new Chart(canvas, {
                type: 'line',
                data: {
                    labels: label,
                    datasets: [
                        {
                            label: option,
                            yAxisID: 'weight-y-axis',
                            data: data,
                            borderWidth: 2,
                            tension: 0.4
                        },
                        {
                            label: 'BMI',
                            yAxisID: 'bmi-y-axis',
                            data: bmiData,
                            borderWidth: 2,
                            tension: 0.4,
                            fill: false
                        },
                    ]
                },
                options: {
                    scales: {
                        'weight-y-axis':{
                            type: 'linear',
                            position: 'left',
                            beginAtZero: false
                        },
                        'bmi-y-axis':{
                            type: 'linear',
                            position: 'right',
                            beginAtZero: false
                        }
                    }
                }
            });
    }else{
        chart = new Chart(canvas, {
        type: 'line',
        data: {
            labels: label,
            datasets: [{
                label: option,
                data: data,
                borderWidth: 2,
                tension: 0.4
            }]
        },
        options: {
            scales: {
                y:{
                    beginAtZero: false
                }
            }
        }
    });
    }
}
