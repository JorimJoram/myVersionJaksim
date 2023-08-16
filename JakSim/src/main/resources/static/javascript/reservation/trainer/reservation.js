var gender = ['남성', '여성'];

function setMyReservation(selectedTIdx) {
    var reservation = document.getElementById('reservation_list');

    axios.get('/reservation/search/' + selectedTIdx)
        .then((response) => {
            if(response.data.length != 0) {
                for(var i = 0; i < response.data.length; i++) {
                    reservation.textContent = "🔥 " + response.data[i]['name'] + "회원   "
                        + " ( " + response.data[i]['id'] + "   / " + gender[response.data[i]['gender']] + " )";
                }
            } else {
                reservation.textContent = "▶ 예약 정보가 없습니다.";
            }
        })
        .catch(error => {
            console.error(error);
        });
}