function fetchAdminSessionInfo(callback) {
    $.ajax({
        url: '/api/admin/universities', // 서버의 어드민 세션 정보를 제공하는 엔드포인트
        type: 'GET',
        xhrFields: {
            withCredentials: true
        },
        dataType: 'json',
        success: function(response) {
            const universityId = response.universityId;
            if (universityId) {
                // 성공적으로 universityId를 받아온 경우, callback 함수를 호출합니다.
                callback(universityId);
            }
        },
        error: function(xhr, status, error) {
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
}
