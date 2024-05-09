package moim_today.global.constant.exception;

public enum MoimExceptionConstant {

    MOIM_NOT_FOUND_ERROR("존재하지 않거나 삭제된 모임입니다."),
    MOIM_MEMBER_DELETE_AUTHORIZED_ERROR("해당 모임의 멤버 삭제 권한이 없습니다."),
    MOIM_HOST_ERROR("해당 모임의 호스트이기 때문에 처리할 수 없습니다."),
    MOIM_CAPACITY_ERROR("해당 모임의 여석이 없습니다."),
    MOIM_FORBIDDEN_ERROR("해당 모임에 대한 접근 권한이 없습니다."),
    NOTICE_NOT_FOUND_ERROR("존재하지 않거나 삭제된 공지입니다."),
    ORGANIZER_FORBIDDEN_ERROR("모임장만 접근할 수 있습니다."),
    JOINED_MOIM_MEMBER_IS_EMPTY("모임에 참여한 멤버가 없습니다.");

    private final String message;

    MoimExceptionConstant(final String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
