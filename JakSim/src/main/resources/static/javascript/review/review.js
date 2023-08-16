function getAvgStar(reviewAvg){
    var trainerId = getTrainerId();

    axios.get(`/review/api/get/avg?ut_idx=${trainerId}`)
        .then((response) => {
            console.log(response.data);
            reviewAvg.innerHTML = response.data.toFixed(1);
        }).catch((error) => {
            console.error(error);
    });
}
function getTrainerId(){
    var currentURL = window.location.href;
    return new URL(currentURL).pathname.split("/").pop();
}