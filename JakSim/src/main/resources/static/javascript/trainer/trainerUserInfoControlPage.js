window.onload = function() {
        var rows = document.querySelectorAll('#table-body tr');

        // Modify ptType and gender values for display
        for (var i = 0; i < rows.length; i++) {
            var ptTypeInput = rows[i].querySelector('[name="ptType"]');
            var genderInput = rows[i].querySelector('[name="gender"]');
            var telInput = rows[i].querySelector('[name="tel"]');

            var ptTypeValue = ptTypeInput.value;
            var genderValue = genderInput.value;
            var telValue = telInput.value;

            // Convert ptType value to text
            if (ptTypeValue === "0") {
                ptTypeInput.value = "개인";
            } else if (ptTypeValue === "1") {
                ptTypeInput.value = "단체";
            }

            // Convert gender value to text
            if (genderValue === "0") {
                genderInput.value = "남자";
            } else if (genderValue === "1") {
                genderInput.value = "여자";
            }

            // Format phone numbers to a specific pattern (e.g., 010-1234-5678)
            telInput.value = formatPhoneNumber(telValue);
        }
    };

    // Function to format phone numbers to a specific pattern (e.g., 010-1234-5678)
    function formatPhoneNumber(phoneNumber) {
        var cleaned = ('' + phoneNumber).replace(/\D/g, '');
        var match = cleaned.match(/^(\d{2,3})(\d{3,4})(\d{4})$/);
        if (match) {
            return match[1] + '-' + match[2] + '-' + match[3];
        }
        return phoneNumber;
    }

document.addEventListener('DOMContentLoaded', function() {
    // 검색 버튼 클릭 이벤트 핸들러 등록
        document.getElementById('search-btn').addEventListener('click', function() {
            // 검색어 입력란의 값을 가져옵니다.
            var searchKeyword = document.getElementById('search-input').value;
            console.log(searchKeyword);

            // 현재 페이지의 URL을 가져옵니다.
            var currentUrl = new URL(window.location.href);

            // 기존 URL에 ptUserName 매개변수가 있는지 확인합니다.
            var hasParam = currentUrl.searchParams.has('ptUserName');

            if (currentUrl.searchParams.get('page') === '1') {
                // 기존 URL에 ptUserName 매개변수가 있으면 값을 변경하고, 없으면 새로 추가합니다.
                if (hasParam) {
                    currentUrl.searchParams.set('ptUserName', searchKeyword);
                } else {
                    currentUrl.searchParams.append('ptUserName', searchKeyword);
                }
            } else {
                // 'page' 매개변수의 값을 1로 설정합니다.
                currentUrl.searchParams.set('page', '1');
                if (hasParam) {
                    currentUrl.searchParams.set('ptUserName', searchKeyword);
                } else {
                    currentUrl.searchParams.append('ptUserName', searchKeyword);
                }
            }

            // 새로운 URL로 이동합니다.
            window.location.href = currentUrl;
        });
});