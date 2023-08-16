var pIdx = -1;
function setPtList() {
    var ptList = document.getElementById('trainer_list');

    axios.get('/product/myPt')
        .then((response) => {
            var ptData = response.data;

            for(var i = 0; i < ptData.length; i++) {
                var option = document.createElement("option");

                option.text = ptData[i]['trainerName'] + " 트레이너";
                option.value = JSON.stringify({pptCnt : ptData[i]['pptCnt'], trainerId : ptData[i]['trainerId'], tType : ptData[i]['tType']});

                ptList.appendChild(option);
            }
            selectedPIdx = ptData[0]['pidx'];

            changePtList(ptData[0], 0, pIdx);
            ptList.addEventListener("change", () => changePtList(ptList.options[ptList.selectedIndex], 1, pIdx));
        })
        .catch(error => {
            console.error(error);
        });
}

// pt권을 변경하는 코드
function changePtList(selectedPt, type) {
    // var ptCnt, trainerId, tType;

    if(type === 0) {
        ptCnt = selectedPt['pptCnt'];
        trainerId = selectedPt['trainerId'];
        tType = selectedPt['ttype'];
    } else if (type === 1) {
        ptCnt = JSON.parse(selectedPt.value).pptCnt;
        trainerId = JSON.parse(selectedPt.value).trainerId;
        tType = JSON.parse(selectedPt.value).tType;
    }

    setPtType(tType);
    setPtCnt(ptCnt);
    setTrainerBrief(trainerId);
    setSchdule(new Date(nowMonth.getFullYear(), nowMonth.getMonth() + 1, nowMonth.getDate()));
}



function setPtCnt(ptCnt) {
    var cnt = document.getElementById("pt_cnt");

    cnt.innerHTML = "남은 PT 횟수 : " + ptCnt + "회";
}

function setPtType(tType) {
    var arr = ['상담', '1:1', '단체'];
    var type = document.getElementById("pt_type");

    type.innerHTML = arr[tType] + " PT권";
}



