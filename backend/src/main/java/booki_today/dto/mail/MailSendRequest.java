package booki_today.dto.mail;

import java.util.List;

public record MailSendRequest(
        String subject,
        String content,
        List<String> to
) {
}
