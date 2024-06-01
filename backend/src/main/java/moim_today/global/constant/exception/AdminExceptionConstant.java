package moim_today.global.constant.exception;

public enum AdminExceptionConstant {

    ADMIN_FORBIDDEN_ERROR("관리자 권한이 없습니다."),
    ADMIN_PASSWORD_ERROR("관리자 비밀번호가 일치하지 않습니다.");

    private final String message;

    AdminExceptionConstant(final String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
