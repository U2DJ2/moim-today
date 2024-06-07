package moim_today.dto.admin.user_inquiry;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import moim_today.dto.mail.MailSendRequest;

import java.util.List;

import static moim_today.global.constant.exception.ValidationExceptionConstant.*;

public record UserInquiryRespondRequest(
        @Min(value = 0, message = USER_INQUIRY_ID_MIN_ERROR) long userInquiryId,
        @Min(value = 0, message = MEMBER_ID_MIN_ERROR) long memberId,
        @NotBlank(message = USER_INQUIRY_RESPONSE_CONTENT_BLANK_ERROR) String responseContent
) {

    public MailSendRequest toMailSendRequest(final String subject, final String userEmail){
        return MailSendRequest.builder()
                .to(List.of(userEmail))
                .subject(subject)
                .build();
    }
}
