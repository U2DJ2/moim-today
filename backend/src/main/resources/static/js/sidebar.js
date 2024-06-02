$(document).ready(function() {
    $("#sidebar-container").load("sidebar.html");

    // 대학교 이름을 가져오는 AJAX 요청
    $.ajax({
        url: "/api/admin/universities/university-name",
        method: "GET",
        xhrFields: {
            withCredentials: true
        },
        success: function(response) {
            // 대학교 이름을 성공적으로 받아온 경우
            $('#university-name').text(response.universityName);
        },
        error: function() {
            // 요청 실패 시 처리
            $('#university-name').text('대학교 이름을 가져오는 데 실패했습니다.');
        }
    });
});