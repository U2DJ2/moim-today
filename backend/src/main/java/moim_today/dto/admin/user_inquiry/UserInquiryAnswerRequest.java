package moim_today.dto.admin.user_inquiry;

import jakarta.validation.constraints.Min;
import lombok.Builder;

@Builder
public record UserInquiryAnswerRequest(
        @Min(value = 1, message = USER_INQUIRY_ID_MIN_ERROR) long userInquiryId,
        boolean answerComplete
) {
    private static final String USER_INQUIRY_ID_MIN_ERROR = "잘못된 문의 ID 값이 입력 되었습니다.";

}
