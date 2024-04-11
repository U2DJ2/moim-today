package booki_today.global.error;

import lombok.Getter;

import static booki_today.global.constant.StatusCodeConstant.METHOD_NOT_ALLOWED;

@Getter
public class MethodNotAllowedException extends RuntimeException {

    private final String statusCode = METHOD_NOT_ALLOWED.statusCode();
    private final String message;

    public MethodNotAllowedException(final String message) {
        this.message = message;
    }
}
