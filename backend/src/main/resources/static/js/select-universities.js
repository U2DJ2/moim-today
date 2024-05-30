$(document).ready(function () {
    loadUniversities();
});

function loadUniversities() {
    $.ajax({
        url: '/api/universities',
        type: 'GET',
        xhrFields: {
            withCredentials: true
        },
        dataType: 'json',
        success: function(data) {
            const universities = data.data;
            const select = $('#university-select');

            universities.forEach(university => {
                const option = $('<option>', {
                    value: university.universityId,
                    text: university.universityName
                });
                select.append(option);
            });
        },
        error: function(xhr, status, error) {
            console.error('Error fetching universities:', error);
        }
    });
}
