$(document).ready(function () {
    let inquiries = []; // 이 변수는 AJAX 요청 결과를 저장하기 위해 여기에 선언됩니다.

    // GET 요청으로 inquiries 데이터 가져오기
    $.ajax({
        url: "http://localhost:8080/api/admin/user-inquiry",
        method: "GET",
        success: function (response) {
            inquiries = response.data; // 여기서 const를 제거하여 상위 스코프의 변수를 사용합니다.
            if (Array.isArray(inquiries)) {
                inquiries.forEach(inquiry => {
                    // forEach 사용
                    const inquiryItem = `
            <div class="card">
                <div class="card-header" id="heading${inquiry.userInquiryId}">
                    <h2 class="mb-0">
                        <button class="btn btn-link btn-block text-left" type="button" data-toggle="collapse" data-target="#collapse${inquiry.userInquiryId}" aria-expanded="true" aria-controls="collapse${inquiry.userInquiryId}">
                            ${inquiry.inquiryTitle} - ${inquiry.createdAt}
                        </button>
                    </h2>
                </div>
                <div id="collapse${inquiry.userInquiryId}" class="collapse" aria-labelledby="heading${inquiry.userInquiryId}" data-parent="#inquiry-list">
                    <div class="card-body">
                        <p><strong>회원 ID:</strong> ${inquiry.memberId}</p>
                        <p><strong>학과:</strong> ${inquiry.departmentName}</p>
                        <p><strong>문의 제목:</strong> ${inquiry.inquiryTitle}</p>
                        <p><strong>문의 내용:</strong> ${inquiry.inquiryContent}</p>
                        <div class="form-group form-check">
                            <input type="checkbox" class="form-check-input answer-complete-checkbox" id="answerComplete${inquiry.userInquiryId}" data-id="${inquiry.userInquiryId}" ${inquiry.answerComplete ? 'checked' : ''}>
                            <label class="form-check-label" for="answerComplete${inquiry.userInquiryId}">답변 완료</label>
                        </div>
                        <p><strong>생성일:</strong> ${inquiry.createdAt}</p>
                        <div class="form-group">
                            <label for="answerContent${inquiry.userInquiryId}">답변:</label>
                            <div class="form-control answer-content editable-div" contenteditable="true" id="answerContent${inquiry.userInquiryId}" data-id="${inquiry.userInquiryId}" rows="4" maxlength="500"></div>
                        </div>
                        <div id="contentCount${inquiry.userInquiryId}" class="char-count">0 / 500</div>
                        <button type="button" class="btn btn-primary" onclick="submitAnswer(${inquiry.userInquiryId}, ${inquiry.memberId})">답변 전송</button>
                    </div>
                </div>
            </div>
            `;
                    $('#inquiry-list').append(inquiryItem);
                });

                // 이벤트 핸들러 및 기타 로직은 여기에 초기화될 수 있습니다.
                $('.btn-link').click(function () {
                    const target = $(this).data('target');
                    $(target).collapse('toggle');
                });

                $(document).on('keyup', '.answer-content', function () {
                    var userInquiryId = $(this).data('id');
                    var charCount = $(this).val().length;
                    $('#contentCount' + userInquiryId).text(charCount + ' / 500');
                });

                $(document).on('change', '.answer-complete-checkbox', function () {
                    var userInquiryId = $(this).data('id');
                    var answerComplete = $(this).is(':checked');
                    updateAnswerCompleteStatus(userInquiryId, answerComplete);
                });

            } else {
                console.error("응답 데이터가 배열 형식이 아닙니다.");
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error("에러 상태: ", textStatus, "에러 메시지: ", errorThrown);
        }
    });
});

function submitAnswer(userInquiryId, memberId) {
    var answerContent = document.getElementById('answerContent' + userInquiryId).textContent;
    var answerData = {userInquiryId: userInquiryId, responseContent: answerContent, memberId: memberId};

    $.ajax({
        url: "http://localhost:8080/api/admin/user-inquiry/response",
        type: "POST",
        contentType: "application/json",
        xhrFields: {
            withCredentials: true
        },
        data: JSON.stringify(answerData),
        success: function (response) {
            alert("답변이 성공적으로 전송되었습니다.");
        },
        error: function (xhr, status, error) {
            alert("답변 전송에 실패했습니다. 다시 시도해주세요.");
        }
    });
}

function updateAnswerCompleteStatus(userInquiryId, answerComplete) {
    var requestData = {userInquiryId: userInquiryId, answerComplete: answerComplete};

    $.ajax({
        url: "http://localhost:8080/api/admin/user-inquiry/answer-complete",
        type: "PATCH",
        contentType: "application/json",
        xhrFields: {
            withCredentials: true
        },
        data: JSON.stringify(requestData),
        success: function (response) {
            alert("답변 완료 상태가 업데이트되었습니다.");
        },
        error: function (xhr, status, error) {
            alert("답변 완료 상태 업데이트에 실패했습니다. 다시 시도해주세요.");
        }
    });
}

