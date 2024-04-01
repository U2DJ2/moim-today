package booki_today.global.constant;

public enum MemberExceptionConstant {

    EMAIL_PASSWORD_ERROR("이메일 또는 비밀번호가 올바르지 않습니다. 다시 확인해주세요.");

    private final String message;

    MemberExceptionConstant(final String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
