package moim_today.dto.admin.user_inquiry;

import moim_today.dto.mail.MailSendRequest;

import java.util.List;

public record UserInquiryRespondRequest(
        long userInquiryId,
        long memberId,
        String responseContent
) {

    public MailSendRequest toMailSendRequest(final String subject, final String userEmail){
        return MailSendRequest.builder()
                .to(List.of(userEmail))
                .subject(subject)
                .build();
    }
}
