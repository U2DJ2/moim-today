$(document).ready(function() {
    $.ajax({
        url: '/api/admin/validate',
        type: 'POST',
        xhrFields: {
            withCredentials: true
        },
        success: function(response) {
            if (response === true) {
                // 관리자 권한이 있는 경우, 페이지를 그대로 표시
                $('#content').show();
            } else {
                // 관리자 권한이 없는 경우, 에러 페이지 표시
                showErrorPage();
            }
        },
        error: function() {
            // API 호출 실패 시 에러 페이지 표시
            showErrorPage();
        }
    });

    function showErrorPage() {
        $('head').html(`
        <style> html, body {
            height: 100%;
            margin: 0;
        }
        .container {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100%;
            text-align: center;
        }
        .row {
            flex-direction: column;
        }
        </style>`)
        $('body').html(`
            <div class="container">
                <div class="row">
                    <div class="col-md-12 text-center">
                        <h1>접근 권한이 없습니다</h1>
                        <p>관리자 권한이 필요합니다. 로그인 페이지로 돌아가 주세요.</p>
                        <button class="btn btn-primary" onclick="redirectToLogin()">로그인 페이지로 돌아가기</button>
                    </div>
                </div>
            </div>
        `);
    }

    window.redirectToLogin = function() {
        window.location.href = "../admin-login.html"; // 로그인 페이지의 URL로 변경
    }
});
