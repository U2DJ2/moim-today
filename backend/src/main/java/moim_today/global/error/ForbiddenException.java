package moim_today.global.error;

import lombok.Getter;

import static moim_today.global.constant.StatusCodeConstant.FORBIDDEN;

@Getter
public class ForbiddenException extends RuntimeException {

    private final String statusCode = FORBIDDEN.statusCode();
    private final String message;

    public ForbiddenException(final String message) {
        this.message = message;
    }
}
