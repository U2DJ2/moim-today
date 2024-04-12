package moim_today.global.constant.exception;

public enum UniversityExceptionConstant {

    UNIVERSITY_NOT_FOUND("을(를) 찾지 못했습니다"),
    UNIVERSITY_SEARCH_CONDITIONS_INSUFFICIENT("검색하기 위한 조건이 부족합니다");
    private final String message;

    UniversityExceptionConstant(final String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
