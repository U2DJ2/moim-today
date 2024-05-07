package moim_today.global.constant.exception;

public enum ScheduleExceptionConstant {

    SCHEDULE_ALREADY_EXIST("해당 시간에 이미 스케줄이 존재합니다."),
    SCHEDULE_FORBIDDEN("해당 스케줄에 대한 접근 권한이 없습니다."),
    SCHEDULE_NOT_FOUND("해당 스케줄을 찾을 수 없습니다."),
    SCHEDULE_COLOR_NOT_FOUND("해당 스케줄 색상 정보를 찾을 수 없습니다.");

    private final String message;

    ScheduleExceptionConstant(final String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
