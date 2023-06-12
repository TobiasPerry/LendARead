$(document).ready(function () {
    $('#myTab a').on('click', function (e) {
        e.preventDefault();
        $(this).tab('show');
    });

    console.log(activeTab)
    if (activeTab === 'lenderReviews') {
        document.getElementById('tab2-tab').classList.add('active');
        document.getElementById('tab2').classList.add('active');
    } else {
        document.getElementById('tab1-tab').classList.add('active');
        document.getElementById('tab1').classList.add('active');
    }
});