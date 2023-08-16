var setTimetableBtn;

window.onload = function () {
    setTimetableBtn = document.getElementById('timetableBtn');
    setTimetableBtn.addEventListener('click', () => {
        window.location.href = '/trainer/trainerControl';
    });

    setSchdule();
}