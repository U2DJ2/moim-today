package booki_today.global.constant;

public enum MailExceptionConstant {

    MAIL_SEND_ERROR("메일 전송 도중에 에러가 발생했습니다. 관리자에게 문의 바랍니다.");

    private final String message;

    MailExceptionConstant(final String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
