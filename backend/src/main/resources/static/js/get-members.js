$(document).ready(function () {
    $('#member-form').on('submit', handleFormSubmit);

    // 삭제 버튼 이벤트 리스너를 동적으로 추가된 요소에도 적용하기 위해
    $('#member-table-body').on('click', '.delete-btn', function() {
        const memberId = $(this).data('member-id');
        deleteMember(memberId);
    });
});

function handleFormSubmit(e) {
    e.preventDefault();
    const departmentId = $('#department-select').val();

    if (!departmentId) {
        alert("학과를 선택하세요.");
        return;
    }

    $.ajax({
        url: `/api/admin/members?departmentId=${departmentId}`,
        type: 'GET',
        xhrFields: {
            withCredentials: true
        },
        dataType: 'json',
        success: function(data) {
            const members = data.data;
            const tableBody = $('#member-table-body');
            tableBody.empty();

            members.forEach(member => {
                const row = `<tr>
                    <td>${member.memberId}</td>
                    <td>${member.universityName}</td>
                    <td>${member.departmentName}</td>
                    <td>${member.email}</td>
                    <td>${member.username}</td>
                    <td>${member.studentId}</td>
                    <td>${member.birthDate}</td>
                    <td>${member.gender}</td>
                    <td><img src="${member.memberProfileImageUrl}" style="width: 50px;"></td>
                    <td><button class="btn btn-danger delete-btn" data-member-id="${member.memberId}">삭제</button></td>
                </tr>`;
                tableBody.append(row);
            });
        },
        error: function(xhr, status, error) {
            console.error('Error fetching members:', error);
        }
    });
}

// 회원 삭제 함수
function deleteMember(memberId) {
    if(confirm("이 회원을 삭제하시겠습니까?")) {
        $.ajax({
            url: `/api/admin/members/${memberId}`,
            type: 'DELETE',
            xhrFields: {
                withCredentials: true
            },
            success: function(result) {
                alert("회원이 삭제되었습니다.");
                $(`button[data-member-id=${memberId}]`).closest('tr').remove();
            },
            error: function(xhr, status, error) {
                alert("회원 삭제에 실패했습니다.");
            }
        });
    }
}
