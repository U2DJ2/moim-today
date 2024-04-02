package booki_today.global.constant;

public enum SessionExceptionConstant {

    MEMBER_SESSION_UNAUTHORIZED("세션이 유효하지 않습니다. 다시 로그인 해주세요."),
    MEMBER_SESSION_JSON_PROCESSING_ERROR("서버 내부 오류가 발생했습니다. 회원 세션을 처리하는 중 문제가 발생했습니다."),
    MEMBER_SESSION_GET_ATTRIBUTE_ERROR("서버 내부 오류가 발생했습니다. MemberSession을 가져오는 도중 오류가 발생했씁니다.");

    private final String message;

    SessionExceptionConstant(final String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
