package moim_today.global.constant;

public enum StatusCodeConstant {

    OK("200"),
    BAD_REQUEST("400"),
    UNAUTHORIZED("401"),
    FORBIDDEN("403"),
    NOT_FOUND("404"),
    METHOD_NOT_ALLOWED("405"),
    INTERNAL_SERVER("500");

    private final String statusCode;

    StatusCodeConstant(final String statusCode) {
        this.statusCode = statusCode;
    }

    public String statusCode() {
        return statusCode;
    }
}
