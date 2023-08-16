window.onload = function() {
    var currentUser = "{{session_user.username}}";
    var reviewElements = document.querySelectorAll('.reviewLink');
    var deleteReviewForm = document.querySelectorAll('.delete_review_td');
    var reviewIdx = document.querySelectorAll('.reviewIdx');

    if (currentUser) {        
        for (var i = 0; i < reviewElements.length; i++) {
            var reviewId = reviewElements[i].textContent;

            if (currentUser.trim() === reviewId.trim()) {
                reviewElements[i].textContent = '';
                currentUser.textContent = '';

                var editLink = document.createElement('a');
                editLink.href = "/review/editReview/" + reviewIdx[i].textContent;
                editLink.className = "jaksim_font";
                editLink.textContent = "수정";

                deleteReviewForm[i].hidden = false;

                reviewElements[i].appendChild(editLink);
            }
            else {
                reviewElements[i].textContent = '';
                deleteReviewForm[i].hidden = true;

            }
        }
    }

    var stars = document.querySelectorAll('.countStar');
    var expert1 = document.querySelectorAll('.expert1');
    var expert2 = document.querySelectorAll('.expert2');


    for(var i=0; i<stars.length; i++) {
        var starCount = parseInt(stars[i].textContent);
        stars[i].textContent = '';

        for(var j=0; j<starCount; j++) {
            var starIcon = document.createElement('i');
            starIcon.className = 'fa-solid fa-star';
            starIcon.style.color = '#f3eb12';
            stars[i].appendChild(starIcon);
        }
        
    }
    setSelectedFilter();
    
if (expert1.length === expert2.length) {
  for (var i = 0; i < expert1.length; i++) {
    var expert1Value = parseInt(expert1[i].textContent);
    var expert2Value = parseInt(expert2[i].textContent);

    // Update the expert labels based on the values
    if (!isNaN(expert1Value)) {
      expert1[i].textContent = getExpertLabel(expert1Value);
    }
    if (!isNaN(expert2Value)) {
      expert2[i].textContent = getExpertLabel(expert2Value);
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

setSelectedFilter();

}


