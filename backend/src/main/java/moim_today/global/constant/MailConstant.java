package moim_today.global.constant;

public enum MailConstant {

    DATA("data"),
    AUTHENTICATION_MAIL("authenticationMail.html"),
    PASSWORD_FIND_MAIL("passwordFindMail.html"),
    EMAIL_CERTIFICATION_MAIL("emailCertificationMail.html"),
    UPCOMING_MEETING_NOTICE_MAIL("upcomingMeetingNoticeMail.html"),
    CERTIFICATION_PAGE("certificationPage"),

    EMAIL_CERTIFICATION_COMPLETE("이메일 인증이 완료되었습니다. 이전 페이지에서 인증 절차를 마무리해주세요."),
    EMAIL_CERTIFICATION_FAIL("이메일 인증 링크가 만료되었습니다. 인증을 다시 시도해주세요."),
    PASSWORD_CERTIFICATION_FAIL("비밀번호 찾기 링크가 만료되었습니다. 다시 시도해주세요."),

    PASSWORD_FIND_SUBJECT("[Moim-Today] 비밀번호 찾기"),
    EMAIL_CERTIFICATION_SUBJECT("[Moim-Today] 회원가입 이메일 인증"),
    MEETING_INVITATION_EMAIL_SUBJECT("[Moim-Today] 다가오는 미팅 정보");

    private final String value;

    MailConstant(final String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
