$(document).ready(function () {
    $('#member-form').on('submit', handleFormSubmit);
});

function handleFormSubmit(e) {
    e.preventDefault();
    const universityId = $('#university-select').val();
    const departmentId = $('#department-select').val();

    if (!universityId) {
        alert("대학을 선택하세요.");
        return;
    }

    if (!departmentId) {
        alert("학과를 선택하세요.");
        return;
    }

    fetch(`/api/admin/members?universityId=${universityId}&departmentId=${departmentId}`, {
        credentials: 'include'
    })
        .then(response => response.json())
        .then(data => {
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
        })
        .catch(error => console.error('Error fetching members:', error));
}
