package moim_today.global.constant.exception;

public enum MeetingExceptionConstant {

    MEETING_NOT_FOUND_ERROR("미팅이 존재하지 않습니다."),
    JOINED_MEETING_NOT_FOUND_ERROR("미팅 참석 정보가 존재하지 않습니다.");

    private final String message;

    MeetingExceptionConstant(final String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
