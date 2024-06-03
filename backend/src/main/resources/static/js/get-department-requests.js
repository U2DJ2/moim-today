$(document).ready(function() {
    $('#load-requests').click(function() {
        $.ajax({
            url: '/api/admin/request-departments',
            type: 'GET',
            success: function(response) {
                var requestTableBody = $('#request-table-body');
                requestTableBody.empty(); // Clear existing table body

                response.data.forEach(function(request) {
                    var row = $('<tr>');
                    row.append($('<td>').text(request.requestDepartmentName));
                    var approveButton = $('<button>').addClass('btn btn-success').text('승인');
                    approveButton.click(function() {
                        approveRequest(request.requestDepartmentId, request.universityId, request.requestDepartmentName, row);
                    });
                    row.append($('<td>').append(approveButton));
                    requestTableBody.append(row);
                });
            },
            error: function(error) {
                alert('학과 요청 정보를 불러오는데 실패했습니다.');
            }
        });
    });

    function approveRequest(requestDepartmentId, universityId, requestDepartmentName, row) {
        $.ajax({
            url: '/api/admin/request-departments',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                requestDepartmentId: requestDepartmentId,
                universityId: universityId,
                requestDepartmentName: requestDepartmentName
            }),
            success: function() {
                row.remove();
                alert('학과 요청이 승인되었습니다.');
            },
            error: function(error) {
                alert('학과 요청 승인에 실패했습니다.');
            }
        });
    }
});
