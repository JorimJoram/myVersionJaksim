/**
@chartDataset 차트에 그려지는 데이터셋. [ ] 형태로 전송
@chartLabel  차트에 그려지는 레이블. { } 형태로 전송
@optionScale 차트 options의 scales에 해당되는 설정값 { } 형태로 전송
@chartType 차트 형태
*/


function showChart(chartDataset, chartLabel, option, chartType, canvas){
    //if(chart){chart.destroy();}
    console.log(canvas);

    return new Chart(canvas, {
            type: chartType,
            data: {
                labels: chartLabel,
                datasets: chartDataset
            },
            options: option
        });
}