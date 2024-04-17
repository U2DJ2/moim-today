package moim_today.dto.mail;

import lombok.Builder;
import moim_today.global.error.BadRequestException;

import java.util.regex.Pattern;

import static moim_today.global.constant.exception.MailExceptionConstant.MAIL_INVALID_FORMAT_ERROR;

@Builder
public record MailValidRequest(
    String userEmail
) {

    public MailValidRequest {
        validateEmail(userEmail);
    }

    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final String NO_OBJECT = null;

    private void validateEmail(String email) {
        if (isNull(email) || email.isBlank() || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new BadRequestException(MAIL_INVALID_FORMAT_ERROR.message());
        }
    }

    private boolean isNull(Object o){
        return o == NO_OBJECT;
    }
}
