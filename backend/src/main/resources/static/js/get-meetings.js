$(document).ready(function() {
    $('#load-meetings').click(function() {
        var moimId = $('#moim-id-input').val();
        if (!moimId) {
            alert('모임 ID를 입력하세요.');
            return;
        }

        $.ajax({
            url: '/api/admin/meetings/' + moimId,
            type: 'GET',
            success: function(response) {
                var meetingTableBody = $('#meeting-table-body');
                meetingTableBody.empty(); // Clear existing table body

                response.forEach(function(meeting) {
                    var row = $('<tr>');
                    row.append($('<td>').text(meeting.meetingId));
                    row.append($('<td>').text(meeting.agenda));
                    row.append($('<td>').text(meeting.startDateTime));
                    row.append($('<td>').text(meeting.endDateTime));
                    row.append($('<td>').text(meeting.place));
                    var deleteButton = $('<button>').addClass('btn btn-danger').text('삭제');
                    deleteButton.click(function() {
                        deleteMeeting(meeting.meetingId, row);
                    });
                    row.append($('<td>').append(deleteButton));
                    meetingTableBody.append(row);
                });
            },
            error: function(error) {
                alert('미팅 정보를 불러오는데 실패했습니다.');
            }
        });
    });

    function deleteMeeting(meetingId, row) {
        $.ajax({
            url: '/api/admin/meetings/' + meetingId,
            type: 'DELETE',
            success: function() {
                row.remove();
                alert('미팅이 삭제되었습니다.');
            },
            error: function(error) {
                alert('미팅 삭제에 실패했습니다.');
            }
        });
    }
});
