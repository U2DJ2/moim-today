$(document).ready(function() {
    $('#load-moims').click(function() {
        $.ajax({
            url: '/api/admin/moims',
            type: 'GET',
            success: function(response) {
                var moimTableBody = $('#moim-table-body');
                moimTableBody.empty(); // Clear existing table body

                response.data.forEach(function(moim) {
                    var row = $('<tr>');
                    row.append($('<td>').text(moim.moimId));
                    row.append($('<td>').text(moim.title));
                    row.append($('<td>').text(moim.capacity));
                    row.append($('<td>').text(moim.currentCount));
                    row.append($('<td>').text(moim.imageUrl));
                    row.append($('<td>').text(moim.moimCategory));
                    row.append($('<td>').text(moim.displayStatus));
                    var deleteButton = $('<button>').addClass('btn btn-danger').text('삭제');
                    deleteButton.click(function() {
                        deleteMoim(moim.moimId, row);
                    });
                    row.append($('<td>').append(deleteButton));
                    moimTableBody.append(row);
                });
            },
            error: function(error) {
                alert('모임 정보를 불러오는데 실패했습니다.');
            }
        });
    });

    function deleteMoim(moimId, row) {
        $.ajax({
            url: '/api/admin/moims/' + moimId,
            type: 'DELETE',
            success: function() {
                row.remove();
                alert('모임이 삭제되었습니다.');
            },
            error: function(error) {
                alert('모임 삭제에 실패했습니다.');
            }
        });
    }
});
