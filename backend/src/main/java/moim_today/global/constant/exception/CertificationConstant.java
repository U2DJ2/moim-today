package moim_today.global.constant.exception;

public enum CertificationConstant {

    CERTIFICATION_EXPIRED_ERROR("인증 만료시간이 지났습니다."),
    CERTIFICATION_TOKEN_NOT_FOUND_ERROR("인증 토큰을 찾을 수 없습니다."),
    CERTIFICATION_EMAIL_NOT_FOUND_ERROR("인증 이메일을 찾을 수 없습니다."),
    EMAIL_NOT_YET_CERTIFICATION_ERROR("아직 해당 이메일이 인증되지 않았습니다.");

    private final String message;

    CertificationConstant(final String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
