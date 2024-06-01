package moim_today.global.constant.exception;

public enum UserInquiryExceptionConstant {

    USER_INQUIRY_NOT_FOUND_ERROR("존재하지 않거나 삭제된 사용자 문의입니다.");

    private final String message;

    UserInquiryExceptionConstant(final String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
