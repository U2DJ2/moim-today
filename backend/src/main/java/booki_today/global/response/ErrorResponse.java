package booki_today.global.response;

public record ErrorResponse(
        String statusCode,
        String message
) {
    public static ErrorResponse of(String statusCode, String message) {
        return new ErrorResponse(statusCode, message);
    }
}
