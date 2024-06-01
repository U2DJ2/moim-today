$(document).ready(function () {
    $('#member-form').on('submit', handleFormSubmit);
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
        type: 'GET', // 요청 방식
        credentials: 'include', // 쿠키를 포함시키기 위해 사용되었던 옵션, $.ajax에서는 이 옵션 대신 xhrFields를 사용
        xhrFields: {
            withCredentials: true
        },
        dataType: 'json', // 응답 데이터 타입
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
                                <td>${member.memberProfileImageUrl}</td>
                            </tr>`;
                tableBody.append(row);
            });
        },
        error: function(xhr, status, error) {
            console.error('Error fetching members:', error);
        }
    });
}
