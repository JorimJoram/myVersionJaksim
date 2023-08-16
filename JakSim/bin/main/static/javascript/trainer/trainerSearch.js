window.onload = function() {
  // Get all the trainer items
var searchExpert1 = document.querySelectorAll('.searchExpert1');
var searchExpert2 = document.querySelectorAll('.searchExpert2');

if (searchExpert1.length === searchExpert2.length) {
  for (var i = 0; i < searchExpert1.length; i++) {
    var expert1Value = parseInt(searchExpert1[i].textContent);
    var expert2Value = parseInt(searchExpert2[i].textContent);

    // Update the expert labels based on the values
    if (!isNaN(expert1Value)) {
      searchExpert1[i].textContent = getExpertLabel(expert1Value);
    }
    if (!isNaN(expert2Value)) {
      searchExpert2[i].textContent = getExpertLabel(expert2Value);
    }
  }
}

function getExpertLabel(value) {
  switch (value) {
    case 0:
      return '#바디프로필';
    case 1:
      return '#파워리프팅';
    case 2:
      return '#다이어트';
    case 3:
      return '#재활운동';
    case 4:
      return '#자세교정';
    case 5:
      return '#컨디셔닝';
    default:
      return '';
  }
} 
}
 

 // Function to handle the message received from the popup window
 function handleMessage(event) {
  if (event.data && event.data.type === 'updateAddress') {
    // Get the full address value from the message
    var fullAddress = event.data.address;

    // Get the second word from the address
    var secondWord = "{{secondWord}}";

    // Update the displayed address only if it matches the secondWord
    if (secondWord && fullAddress.includes(secondWord)) {
      document.getElementById('addressValueSpan').textContent = fullAddress;
    }
  }
}

// Attach the message event listener
window.addEventListener('message', handleMessage);

function road() {
  var originalAddress = "{{address}}";
  const popup = window.open("/address-search","Popup", "width=800, height=700");

  popup.postMessage({
      type: 'originalAddress',
      address: originalAddress
    }, '*');

  popup.onbeforeunload = function () {
    // Get the selected address value from the popup window
    var addressInput = popup.document.getElementById("selected-address");
    var addressValue = addressInput.value;

    // Call the updateTrainerList function passing the selected address value
    updateTrainerList(addressValue);
  };
}

// Function to update the trainer list based on the selected address
function updateTrainerList(address) {
  // Get the current URL and create a new URL with the updated address parameter
  var currentUrl = new URL(window.location.href);

              // 현재 페이지가 1일 때만 필터를 적용합니다.
      if (currentUrl.searchParams.get('page') === '1') {
          // 'filter' 매개변수의 값을 설정합니다.
          currentUrl.searchParams.set("address", address);
      } else {
          // 'page' 매개변수의 값을 1로 설정합니다.
          currentUrl.searchParams.set('page', '1');
          // 'filter' 매개변수의 값을 설정합니다.
          currentUrl.searchParams.set("address", address);
      }
  

  // Reload the page with the updated address parameter
  window.location.href = currentUrl;

  document.getElementById('input-address').style.display = 'none';

}

document.addEventListener('DOMContentLoaded', function() {
  // 버튼 클릭 이벤트 핸들러 등록
$('.toggle-btn').on('click', function() {
  // 클릭한 버튼의 값을 가져와서 filter 값으로 설정합니다.
  var filterValue = $(this).val();
  // 현재 페이지의 URL을 가져옵니다.
  var currentUrl = new URL(window.location.href);

          // 현재 페이지가 1일 때만 필터를 적용합니다.
  if (currentUrl.searchParams.get('page') === '1') {
      // 'filter' 매개변수의 값을 설정합니다.
      currentUrl.searchParams.set('filter', filterValue);
  } else {
      // 'page' 매개변수의 값을 1로 설정합니다.
      currentUrl.searchParams.set('page', '1');
      // 'filter' 매개변수의 값을 설정합니다.
      currentUrl.searchParams.set('filter', filterValue);
  }
  
window.location.href = currentUrl;
  
});

});