
// 이미지 미리보기
function previewImage(event) {
    var input = event.target;
    var reader = new FileReader();

    reader.onload = function(){
        var preview = document.getElementById('preview-image');
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
            previewImage.style.maxWidth = '500px';
            previewImage.style.maxHeight = '500px';
            previewContainer.appendChild(previewImage);
        };

        reader.readAsDataURL(input.files[i]);
    }
}

// price 표시
function inputNumberFormat(obj) {
    obj.value = comma(uncomma(obj.value));
}

function comma(str) {
    str = String(str);
    return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
}

function uncomma(str) {
    str = String(str);
    return str.replace(/[^\d]+/g, '');
}


// 주소찾기 모달창
function road() {
    const popup = window.open("/address-search2","Popup", "width=800, height=700");

    popup.onbeforeunload = function(data) {
      console.log(data)
      console.log("데이터 체크")
    }

    // 팝업창에 데이터 쓰기
    popup.document.write(data);
}

// 자격증, 경력, PT 추가 버튼
var certClicked = false;
var careerClicked = false;
var ptClicked = false;

    document.getElementById('add-cert').onclick = function(){
    var certInput = document.getElementById('certName');
    var create_cert_div = document.getElementById('create_cert_div');
    var cert_span = document.createElement('input');

    if(certInput.value){
        cert_span.value = certInput.value; 
        cert_span.setAttribute('class', 'jaksim_btn');
        cert_span.setAttribute('name', 'certName');
        cert_span.style.margin = '3px';

        create_cert_div.appendChild(cert_span);
        certInput.value = '';
    }

    certClicked = true;
}


        document.getElementById('add-career').onclick = function(){
            var careerInput = document.getElementById('careerContent');
            var create_career_div = document.getElementById('create_career_div');
            var career_span = document.createElement('input');

            if(careerInput.value){
                career_span.value = careerInput.value;
                career_span.setAttribute('class', 'jaksim_btn');
                career_span.setAttribute('name', 'careerContent');
                career_span.style.margin = '3px';

                create_career_div.appendChild(career_span);
                careerInput.value = '';
            }

            careerClicked = true;
        }


  
    document.getElementById('add-pt').onclick = function () {
        var type = document.querySelector('input[id="ptType"]:checked');
        var times = document.getElementById('ptTimes');
        var title = document.getElementById('ptTitle');
        var price = document.getElementById('ptPrice');
        var period = document.getElementById('ptPeriod');

        var create_pt_div = document.getElementById('create_pt_div');

        if (type.value && times.value && title.value && price.value && period.value) {
            var pt_container = document.createElement('div');
            pt_container.className = 'pt-container';

            let displayText = '';
            if (parseInt(type.value) === 0)
                displayText += '개인';
            else
                displayText += '단체';


            var formattedPrice = removeCommas(price.value);
            
            pt_container.innerHTML = `
                <span>PT 상품</span><br>
                <input value="${type.value}" name="ptType" hidden="hidden" type="number"/>
                <input value="${times.value}" name="ptTimes" hidden="hidden"  type="number"/>
                <input value="${title.value}" name="ptTitle" hidden="hidden" type="text" />
                <input value="${formattedPrice}" name="ptPrice" hidden="hidden"  type="number" />
                <input value="${period.value}" name="ptPeriod" hidden="hidden"  type="number"/>
                <span>`+ displayText + ` ${times.value}회 ${title.value} ${price.value}원 ${period.value}일</span>`
             ;

            create_pt_div.appendChild(pt_container);
            console.log(pt_container);

            times.value = '';
            title.value = '';
            price.value = '';
            type.value = '';
            period.value = '';
        }
        ptClicked = true;
    }

    function removeCommas(str) {
    return str.replace(/,/g, '');
    }

    
 // 제출 전 확인
 document.getElementById('trainerForm').onsubmit = function() {
    // Check if all buttons have been clicked
    if (!certClicked) {
        alert("자격증 추가 버튼을 눌러주세요.");
        return false;
    }
    if (!careerClicked) {
        alert("경력사항 추가 버튼을 눌러주세요.");
        return false;
    }
    if (!ptClicked) {
        alert("PT 등록 버튼을 눌러주세요.");
        return false;
    }

    // 주소 값 입력 확인
    var addressInput = document.getElementById('input-address');
    if (addressInput.value.trim() === '') {
        alert("주소를 입력하세요.");
        return false; // Prevent form submission
    }

    // 전문분야 입력 확인
    var expert1 = document.querySelector('select[name="expert1"]').value;
    var expert2 = document.querySelector('select[name="expert2"]').value;

    if (expert1 === expert2) {
        alert("전문분야는 서로 다른 값을 선택해야 합니다.");
        return false; // Prevent form submission
    }

    return true; // Allow form submission
};