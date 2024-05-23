package moim_today.global.constant.exception;

public enum MeetingExceptionConstant {

    MEETING_NOT_FOUND_ERROR("미팅이 존재하지 않습니다."),
    JOINED_MEETING_NOT_FOUND_ERROR("미팅 참석 정보가 존재하지 않습니다."),
    MEETING_COMMENT_FORBIDDEN_ERROR("댓글 작성자만 접근할 수 있습니다."),
    MEETING_FORBIDDEN_ERROR("해당 미팅에 대한 권한이 없습니다.");

    private final String message;

    MeetingExceptionConstant(final String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
