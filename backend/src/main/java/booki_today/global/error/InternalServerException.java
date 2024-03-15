package booki_today.global.error;

import lombok.Getter;

import static booki_today.global.constant.StatusCodeConstant.INTERNAL_SERVER;

@Getter
public class InternalServerException extends RuntimeException {

    private final String statusCode = INTERNAL_SERVER.statusCode();
    private final String message;

    public InternalServerException(final String message) {
        this.message = message;
    }
}
