package moim_today.global.constant;

public enum MailConstant {

    DATA("data"),
    AUTHENTICATION_MAIL("authenticationMail.html"),
    PASSWORD_FIND_MAIL("passwordFindMail.html"),
    PASSWORD_FIND_SUBJECT("[Moim-Today] 비밀번호 찾기");

    private final String value;

    MailConstant(final String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
