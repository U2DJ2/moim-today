package moim_today.global.constant.exception;

public enum MeetingCommentExceptionConstant {

    MEETING_COMMENT_NOT_FOUND_ERROR("미팅 댓글이 존재하지 않습니다.");

    private final String message;

    MeetingCommentExceptionConstant(final String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
