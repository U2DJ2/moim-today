package moim_today.global.constant.exception;

public enum MoimExceptionConstant {

    MOIM_NOT_FOUND_ERROR("존재하지 않거나 삭제된 모임입니다."),
    MOIM_MEMBER_DELETE_AUTHORIZED_ERROR("해당 모임의 멤버 삭제 권한이 없습니다."),
    MOIM_HOST_ERROR("해당 모임의 호스트이기 때문에 처리할 수 없습니다."),
    MOIM_CAPACITY_ERROR("해당 모임의 여석이 없습니다."),
    MOIM_FORBIDDEN_ERROR("해당 모임에 대한 접근 권한이 없습니다."),
    NOTICE_NOT_FOUND_ERROR("존재하지 않거나 삭제된 공지입니다."),
    ORGANIZER_FORBIDDEN_ERROR("모임장만 접근할 수 있습니다."),
    JOINED_MOIM_MEMBER_IS_EMPTY("모임에 참여한 멤버가 없습니다."),
    VIEWED_MOIM_JSON_PROCESSING_ERROR("서버 내부 오류가 발생했습니다. 조회수를 처리하는 중 문제가 발생했습니다."),
    VIEWED_MOIM_NOT_FOUND_ERROR("서버 내부 오류가 발생했습니다. 모임 ID를 쿠키에서 조회하는 중 문제가 발생했습니다."),
    PRIVATE_MOIM_NEEDS_PASSWORD_ERROR("비공개 모임은 비밀번호를 입력해야 합니다.");

    private final String message;

    MoimExceptionConstant(final String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
