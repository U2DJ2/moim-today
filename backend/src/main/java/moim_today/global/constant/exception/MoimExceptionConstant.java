package moim_today.global.constant.exception;

public enum MoimExceptionConstant {

    MOIM_NOT_FOUND_ERROR("존재하지 않거나 삭제된 모임입니다."),
    MOIM_FORBIDDEN("해당 모임에 대한 접근 권한이 없습니다.");

    private final String message;

    MoimExceptionConstant(final String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
