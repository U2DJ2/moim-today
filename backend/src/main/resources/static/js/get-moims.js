$(document).ready(function() {
    $('#load-moims').click(function() {
        $.ajax({
            url: '/api/admin/moims',
            type: 'GET',
            success: function(response) {
                var moimTableBody = $('#moim-table-body');
                moimTableBody.empty(); // 기존 테이블 바디를 비웁니다

                response.data.forEach(function(moim) {
                    var row = $('<tr>');
                    row.append($('<td>').text(moim.moimId));
                    row.append($('<td>').text(moim.title));
                    row.append($('<td>').text(moim.capacity));
                    row.append($('<td>').text(moim.currentCount));
                    // 이미지 URL을 사용하여 <img> 태그를 생성하고 src 속성에 할당합니다. 크기를 100px로 설정합니다.
                    var imgElement = $('<img>').attr('src', moim.imageUrl).css('width', '100px');
                    row.append($('<td>').append(imgElement)); // 이미지 요소를 td에 추가합니다.
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
