package moim_today.dto.admin.user_inquiry;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import moim_today.dto.mail.MailSendRequest;

import java.util.List;

public record UserInquiryRespondRequest(
        @Min(value = 1, message = USER_INQUIRY_ID_MIN_ERROR) long userInquiryId,
        @Min(value = 1, message = MEMBER_ID_MIN_ERROR) long memberId,
        @NotBlank(message = RESPONSE_CONTENT_BLANK_ERROR) String responseContent
) {

    private static final String USER_INQUIRY_ID_MIN_ERROR = "잘못된 문의 ID 값이 입력 되었습니다.";
    private static final String MEMBER_ID_MIN_ERROR = "잘못된 회원 ID 값이 입력 되었습니다.";
    private static final String RESPONSE_CONTENT_BLANK_ERROR = "답변 내용은 공백일 수 없습니다.";

    public MailSendRequest toMailSendRequest(final String subject, final String userEmail){
        return MailSendRequest.builder()
                .to(List.of(userEmail))
                .subject(subject)
                .build();
    }
}
