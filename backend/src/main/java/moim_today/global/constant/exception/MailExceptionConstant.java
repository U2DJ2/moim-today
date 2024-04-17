package moim_today.global.constant.exception;

public enum MailExceptionConstant {

    MAIL_SEND_ERROR("메일 전송 도중에 에러가 발생했습니다. 관리자에게 문의 바랍니다."),
    MAIL_CERTIFICATION_TOKEN_NOT_FOUND_ERROR("메일 인증 정보를 찾을 수 없습니다."),
    MAIL_INVALID_FORMAT_ERROR("올바른 메일 형식이 아닙니다.");

    private final String message;

    MailExceptionConstant(final String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
