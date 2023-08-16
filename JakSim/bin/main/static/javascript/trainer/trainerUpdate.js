

function checkExpertValues() {
        var expert1 = document.querySelector('select[name="expert1"]').value;
        var expert2 = document.querySelector('select[name="expert2"]').value;

        if (expert1 === expert2) {
            alert("전문분야는 서로 다른 값을 선택해야 합니다.");
            return false; // Prevent form submission
        }

        return true; // Allow form submission
    }

function previewImage(event) {
    var input = event.target;
    var reader = new FileReader();

    reader.onload = function(){
        var preview = document.getElementById('preview-profile-image');
        preview.src = reader.result;
        preview.style.display = 'block';
    };

    reader.readAsDataURL(input.files[0]);
}

function previewImage2(event) {
    var input = event.target;
    var previewContainer = document.getElementById('image-preview-container');
    previewContainer.innerHTML = ''; // 기존의 미리보기 이미지를 모두 지웁니다.

    for (var i = 0; i < input.files.length; i++) {
        var reader = new FileReader();

        reader.onload = function (e) {
            var previewImage = document.createElement('img');
            previewImage.src = e.target.result;
            previewImage.style.maxWidth = '100px';
            previewImage.style.maxHeight = '100px';
            previewContainer.appendChild(previewImage);
        };

        reader.readAsDataURL(input.files[i]);
    }
}

  //주소찾기
  function road() {
    const popup = window.open("/address-search2","Popup", "width=800, height=700");

    popup.onbeforeunload = function(data) {
      console.log(data)
      console.log("데이터 체크")
    }

    // 팝업창에 데이터 쓰기
    popup.document.write(data);
  }

