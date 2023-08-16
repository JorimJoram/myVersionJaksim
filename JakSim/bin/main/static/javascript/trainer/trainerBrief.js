function setTrainerBrief(trainerId) {
    var trainerData;
    var url = '/trainer/briefInfo/' + trainerId;
    var expertSample = ['바디 프로필', '파워 리프팅', '다이어트', '재활 운동', '자세 교정', '컨디셔닝'];
    var gender = ['남자', '여자'];

    axios.get(url)
        .then((response) => {
            trainerData = response.data;

console.log(trainerData);
console.log(gender[trainerData['gender']]);
console.log(expertSample[trainerData['expert1']]);
            document.getElementById("trainer-info").src = trainerData['imagePath'];
            document.getElementById("trainerName").innerText = trainerData['userName'] + " 트레이너";
            document.getElementById("trainerName").href = "/trainer/" + trainerData['userId'];
            document.getElementById("gender").innerText = gender[trainerData['gender']];
            document.getElementById("insta").href = trainerData['insta'];
            document.getElementById("gym").innerText = trainerData['gym'];
            document.getElementById("expert").innerText = expertSample[trainerData['expert1']] + ", " + expertSample[trainerData['expert2']];
        })
        .catch(error => {
            console.error(error);
        });
}