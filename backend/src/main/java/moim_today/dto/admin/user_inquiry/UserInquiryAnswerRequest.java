package moim_today.dto.admin.user_inquiry;

import lombok.Builder;

@Builder
public record UserInquiryAnswerRequest(
        long userInquiryId,
        boolean answerComplete
) {
}
