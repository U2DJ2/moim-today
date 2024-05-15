package moim_today.global.constant.exception;

public enum TodoExceptionConstant {

    TODO_NOT_FOUND_ERROR("존재하지 않거나 삭제된 투두입니다."),
    TODO_NOT_OWNER_ERROR("해당 투두에 수정 또는 삭제할 권한이 없습니다.");

    private final String message;

    TodoExceptionConstant(final String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
