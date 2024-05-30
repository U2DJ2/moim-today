$(document).ready(function () {
    $('#university-select').on('change', handleUniversityChange);
});

function handleUniversityChange() {
    const universityId = $(this).val();
    const departmentSelect = $('#department-select');
    departmentSelect.empty();
    departmentSelect.append('<option value="">학과를 선택하세요</option>');

    if (universityId) {
        fetch(`/api/departments/university-id?universityId=${universityId}`, {
            credentials: 'include'
        })
            .then(response => response.json())
            .then(data => {
                const departments = data.data;

                departments.forEach(department => {
                    const option = $('<option>', {
                        value: department.departmentId,
                        text: department.departmentName
                    });
                    departmentSelect.append(option);
                });
            })
            .catch(error => console.error('Error fetching departments:', error));
    }
}
