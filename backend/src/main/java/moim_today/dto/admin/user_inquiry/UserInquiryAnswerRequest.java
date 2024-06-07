package moim_today.dto.admin.user_inquiry;

import jakarta.validation.constraints.Min;
import lombok.Builder;

import static moim_today.global.constant.exception.ValidationExceptionConstant.USER_INQUIRY_ID_MIN_ERROR;

@Builder
public record UserInquiryAnswerRequest(
        @Min(value = 0, message = USER_INQUIRY_ID_MIN_ERROR) long userInquiryId,
        boolean answerComplete
) {

}
