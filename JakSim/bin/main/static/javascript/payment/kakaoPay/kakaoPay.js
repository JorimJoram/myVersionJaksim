function kakaoPay(ptTitle, tpIdx, ptPrice, ptTimes, ptPeriod) {
     var data = {
         ptTitle: ptTitle,
         tpIdx: tpIdx,
         ptPrice: ptPrice,
         ptTimes: ptTimes,
         ptPeriod: ptPeriod
     };

    axios.post('/payment/ready', data)
        .then((response) => {
            var httpStatus = response.status;

            console.log(response);

            if(httpStatus == 500) {
                alert("500: Payment with Kakao Pay failed.");
            } else {
                var box = response.data.next_redirect_pc_url;

                window.open(box, "", "width=500, height=800");
            }
        })
        .catch(error => {
            console.error(error);
        });
}