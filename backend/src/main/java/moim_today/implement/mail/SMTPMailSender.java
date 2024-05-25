package moim_today.implement.mail;

import moim_today.dto.mail.MailSendRequest;

public interface SMTPMailSender {

    void send(final MailSendRequest mailSendRequest, final String contentTemplate, final String data);
}
