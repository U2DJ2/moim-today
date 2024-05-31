package moim_today.dto.admin.user_inquiry;

import moim_today.dto.mail.MailSendRequest;

import java.util.List;

public record UserInquiryRespondRequest(
        long userInquiryId,
        String userEmail,
        String responseTitle,
        String responseContent
) {

    public MailSendRequest toMailSendRequest(final String subject){
        return MailSendRequest.builder()
                .to(List.of(userEmail))
                .subject(subject)
                .build();
    }
}
