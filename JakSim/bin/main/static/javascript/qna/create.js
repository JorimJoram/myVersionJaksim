window.onload = function(){
    test();
}

function test(){
    axios.get(`/qna/api/get/myquestion`)
        .then(response => {
            console.log(response.data);
            console.log(response.data[0]['parent_idx']);
        }).catch(error => {
            console.error(error);
        });
}