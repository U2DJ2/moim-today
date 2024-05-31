package moim_today.dto.admin.user_inquiry;

public record UserInquiryRespondRequest(
        long userInquiryId,
        String userEmail,
        String responseTitle,
        String responseContent
) {
}
