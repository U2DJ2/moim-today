function submitQuery() {
    var inquiryTitle = document.getElementById('inquiryTitle').value;
    var inquiryContent = document.getElementById('inquiryContent').value;
    var userQuery = { title: inquiryTitle, content: inquiryContent };

    $.ajax({
        url: "http://localhost:8080/api/admin/user-inquiry",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(userQuery),
        success: function(response) {
            alert("문의사항이 성공적으로 전송되었습니다.");
        },
        error: function(xhr, status, error) {
            alert("문의사항 전송에 실패했습니다. 다시 시도해주세요.");
        }
    });
}

// 글자 수 제한 및 표시 스크립트
$(document).ready(function() {
    $('#inquiryTitle').keyup(function() {
        var charCount = $(this).val().length;
        $('#titleCount').text(charCount + ' / 50');
    });

    $('#inquiryContent').keyup(function() {
        var charCount = $(this).val().length;
        $('#contentCount').text(charCount + ' / 500');
    });
});
