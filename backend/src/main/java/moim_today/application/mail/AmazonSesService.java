package moim_today.application.mail;

import moim_today.dto.mail.MailSendRequest;
import moim_today.implement.mail.MailSender;
import org.springframework.stereotype.Service;

@Service
public class AmazonSesService implements MailService{

    private final MailSender mailSender;

    public AmazonSesService(final MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void send(final MailSendRequest mailSendRequest) {
        mailSender.send(mailSendRequest);
    }
}
