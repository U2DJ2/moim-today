package moim_today.global.error;

import lombok.Getter;

import static moim_today.global.constant.StatusCodeConstant.*;

@Getter
public class BadRequestException extends RuntimeException {

    private final String statusCode = BAD_REQUEST.statusCode();
    private final String message;

    public BadRequestException(final String message) {
        this.message = message;
    }
}
