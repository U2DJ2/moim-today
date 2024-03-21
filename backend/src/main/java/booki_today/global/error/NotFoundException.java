package booki_today.global.error;

import lombok.Getter;

import static booki_today.global.constant.StatusCodeConstant.NOT_FOUND;

@Getter
public class NotFoundException extends RuntimeException {

    private final String statusCode = NOT_FOUND.statusCode();
    private final String message;

    public NotFoundException(final String message) {
        this.message = message;
    }
}
