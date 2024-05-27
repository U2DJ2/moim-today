$(document).ready(function () {
    loadUniversities();
});

function loadUniversities() {
    fetch('/api/universities', {
        credentials: 'include'
    })
        .then(response => response.json())
        .then(data => {
            const universities = data.data;
            const select = $('#university-select');

            universities.forEach(university => {
                const option = $('<option>', {
                    value: university.universityId,
                    text: university.universityName
                });
                select.append(option);
            });
        })
        .catch(error => console.error('Error fetching universities:', error));
}
