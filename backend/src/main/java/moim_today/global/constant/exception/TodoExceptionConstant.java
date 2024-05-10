package moim_today.global.constant.exception;

public enum TodoExceptionConstant {

    TODO_START_TIME_AFTER_END_TIME_ERROR("시작 시간이 끝나는 시간보다 뒤에 있습니다.");

    private final String message;

    TodoExceptionConstant(final String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
