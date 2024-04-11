package moim_today.dto.mail;

import lombok.Builder;

import java.util.List;

@Builder
public record MailSendRequest(
        String subject,
        List<String> to
) {

    public static MailSendRequest of(final String subject, final List<String> to) {
        return MailSendRequest.builder()
                .subject(subject)
                .to(to)
                .build();
    }
}
