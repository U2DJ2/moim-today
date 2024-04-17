package moim_today.global.constant.exception;

public enum MemberExceptionConstant {

    MEMBER_NOT_FOUND_ERROR("회원이 존재하지 않습니다."),
    EMAIL_NOT_FOUND_ERROR("이메일이 존재하지 않습니다."),
    EMAIL_PASSWORD_ERROR("이메일 또는 비밀번호가 올바르지 않습니다. 다시 확인해주세요."),
    EMAIL_ALREADY_USED_ERROR("이미 가입한 이메일입니다."),
    NO_PASSWORD("패스워드가 없습니다."),
    NO_USERNAME("유저네임이 없습니다.");

    private final String message;

    MemberExceptionConstant(final String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
