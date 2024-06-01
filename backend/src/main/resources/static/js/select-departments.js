$(document).ready(function () {
    loadDepartments();
});

function loadDepartments() {
    // fetchAdminSessionInfo 함수 호출
    fetchAdminSessionInfo(function(universityId) {
        const departmentSelect = $('#department-select');
        departmentSelect.empty();
        departmentSelect.append('<option value="">학과를 선택하세요</option>');

        $.ajax({
            url: `/api/departments/university-id?universityId=${universityId}`,
            type: 'GET',
            xhrFields: {
                withCredentials: true
            },
            dataType: 'json',
            success: function(data) {
                const departments = data.data;

                departments.forEach(department => {
                    const option = $('<option>', {
                        value: department.departmentId,
                        text: department.departmentName
                    });
                    departmentSelect.append(option);
                });
            },
            error: function(xhr, status, error) {
                console.error('Error fetching departments:', error);
            }
        });
    });
}
