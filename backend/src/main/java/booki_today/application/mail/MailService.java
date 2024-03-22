package booki_today.application.mail;

import booki_today.dto.mail.MailSendRequest;

public interface MailService {

    void send(final MailSendRequest mailSendRequest);
}
