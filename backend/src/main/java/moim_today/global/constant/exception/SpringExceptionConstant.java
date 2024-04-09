package moim_today.global.constant.exception;

public enum SpringExceptionConstant {

    METHOD_ARGUMENT_TYPE_MISMATCH("HTTP 파라미터 타입이 일치하지 않습니다."),
    MISSING_SERVLET_REQUEST_PARAMETER("HTTP 필수 파라미터가 누락되었습니다."),
    NO_HANDLER_FOUND("HTTP 요청을 처리할 컨트롤러가 존재하지 않습니다."),
    METHOD_NOT_ALLOWED("HTTP 메소드 형식이 일치하지 않습니다.");

    private final String message;

    SpringExceptionConstant(final String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
