package moim_today.global.constant.exception;

public enum CertificationConstant {

    CERTIFICATION_EXPIRED_ERROR("인증 만료시간이 지났습니다.");

    private final String message;

    CertificationConstant(final String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
