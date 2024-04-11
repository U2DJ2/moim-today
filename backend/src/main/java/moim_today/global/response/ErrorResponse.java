package moim_today.global.response;

public record ErrorResponse(
        String statusCode,
        String message
) {
    public static ErrorResponse of(final String statusCode, final String message) {
        return new ErrorResponse(statusCode, message);
    }
}
