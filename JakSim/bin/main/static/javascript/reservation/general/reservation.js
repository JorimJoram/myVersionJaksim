var formattedDate;
var type = ['상담', '1:1', '단체'];


function setDate(date) {
    document.getElementById("reservation_date").innerText = date;

    setMyReservation(date);
}

function setMyReservation(date) {
    var reservation = document.getElementById('reservation_list');
    var ptList = document.getElementById('trainer_list');
    // var canBtn = document.getElementById('resCancleBtn');
    var trainerId = JSON.parse(ptList.options[ptList.selectedIndex].value).trainerId;
    formattedDate = date.split(". ").join("-");

    const data = {
        trainerId: trainerId,
        dt: formattedDate
    };

    axios.post('/reservation/search', data)
        .then((response) => {
            console.log("reservation 확인");
            console.log(response.data);
            if(response.data['tstartT'] != null) {
                reservation.textContent = response.data['tstartT'] + " - " + response.data['tendT'] + " ( " + type[response.data['ttype']] + " )";

                canBtn.style.display = 'inline-block';

                selectedPIdx = response.data['pidx'];
                selectedRIdx = response.data['ridx'];
                selectedTType = type[response.data['ttype']];
            } else {
                reservation.textContent = "▶ 예약 정보가 없습니다.";
                canBtn.style.display = 'none';
            }
        })
        .catch(error => {
            console.error(error);
        });
}

function resRegister(formattedDate) {
    var radios = document.getElementsByName("timetableRadio");

    for(var radio of radios) {
        if(radio.checked) {
            tIdx = radio.value;
        }
    }

    var warn = ["지난 날에 대한 예약은 할 수 없습니다.",
                "이미 등록된 예약이 있습니다.",
                "이미 등록된 예약이 있습니다.",
                "해당 시간표는 존재하지 않습니다.",
                "예약이 올바르게 되지 않았습니다.",
                "예약이 올바르게 되었습니다."
                ];

    const data = {
        p_idx: selectedPIdx,
        t_idx: tIdx,
        trainerId: trainerId,
        ptCnt: ptCnt,
        date: formattedDate
    };

    axios.post('/reservation/register', data)
        .then((response) => {
            alert(warn[response.data['reservationStatus']]);
console.log(response.data);
            if(response.data['reservationStatus'] == 5) {
                setMyReservation(formattedDate);
                setSchdule();
                setPtCnt(response.data['ptCnt']);
            }
        })
        .catch(error => {
            console.error(error);
        });
    tIdx = -1;
}

function resCancle() {
    var url = '/reservation/cancle/' + selectedPIdx + '/' + selectedRIdx;

    axios.get(url)
        .then((response) => {
            if(response.data) {
                alert(type[tType] + ' 예약이 취소되었습니다!');
                setMyReservation(document.getElementById("reservation_date").innerText);
                setSchdule();
                setPtCnt(response.data['ptCnt']);
            } else {
                alert('예약이 취소되지 않았습니다!');
            }
        })
        .catch(error => {
            console.error(error);
        });
}