package moim_today.global.constant.exception;

public enum ScheduleExceptionConstant {

    SCHEDULE_ALREADY_EXIST("해당 시간에 이미 스케줄이 존재합니다.");

    private final String message;

    ScheduleExceptionConstant(final String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
