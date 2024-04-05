package moim_today.global.constant;

public enum FileExceptionConstant {

    FILE_UPLOAD_ERROR("파일 업로드 도중에 에러가 발생했습니다. 관리자에게 문의 바랍니다."),
    FILE_DELETE_ERROR("파일 삭제 도중에 에러가 발생했습니다. 관리자에게 문의 바랍니다.");
    private final String message;

    FileExceptionConstant(final String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
