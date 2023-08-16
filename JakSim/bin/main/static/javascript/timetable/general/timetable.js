function setTimetable(date) {
    var type = ['상담', '1:1', '단체'];
    var formattedDate = date.split(". ").join("-");
    var timetableList = document.getElementById("timetableList");

    const data = {
        trainerId: trainerId,
        dt: formattedDate,
        type: tType
    }

    axios.post('/timetable/details', data)
        .then((response) => {
            var childDivs = timetableList.querySelectorAll("div");

            childDivs.forEach(function (childDiv) {
                timetableList.removeChild(childDiv);
            });

            if(response.data.length == 0) {
                var timetable = document.createElement("div");
                timetable.innerHTML = "※ 시간표 정보가 없습니다.";
                timetableList.appendChild(timetable);

                resBtn.style.display = 'none';
            } else {
                for(var i = 0; i < response.data.length; i++) {
                    radioBox = document.createElement("input");
                    var timetable = document.createElement("div");

                    timetable.innerHTML = " " + response.data[i]['tstartT'].substr(0, 5) + " - " + response.data[i]['tendT'].substr(0, 5) + " ( " + type[response.data[i]['ttype']] + " )";

                    if(response.data[i]['ttype'] == 2) {
                        timetable.innerHTML += "_예약 가능 인원 : " + response.data[i]['tpeople'];
                    }


                    radioBox.setAttribute("type", "radio");
                    radioBox.setAttribute("name", ("timetableRadio"));
                    radioBox.setAttribute("value", response.data[i]['tidx']);
                    // radioBox.setAttribute("disabled", "false"); // 인원수 체크
                    timetable.insertBefore(radioBox, timetable.firstChild);

                    timetableList.appendChild(timetable);
                }

                resBtn.style.display = 'inline-block';
            }
        }).catch(error => {
            console.error(error);
        });
}