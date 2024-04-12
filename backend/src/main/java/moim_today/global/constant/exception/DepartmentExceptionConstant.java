package moim_today.global.constant.exception;

public enum DepartmentExceptionConstant {

    DEPARTMENT_NOT_FOUND_ERROR("존재하지 않는 전공입니다."),
    DEPARTMENT_NOT_MATCH_UNIVERSITY("귀하의 학교에 존재하는 전공이 아닙니다.");

    private final String message;

    DepartmentExceptionConstant(final String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
