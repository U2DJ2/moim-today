package moim_today.global.error;

import lombok.Getter;

import static moim_today.global.constant.StatusCodeConstant.UNAUTHORIZED;

@Getter
public class UnauthorizedException extends RuntimeException {

    private final String statusCode = UNAUTHORIZED.statusCode();
    private final String message;

    public UnauthorizedException(final String message) {
        this.message = message;
    }
}
