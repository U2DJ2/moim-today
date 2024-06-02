package moim_today.application.mail;

import moim_today.dto.mail.MailSendRequest;
import moim_today.implement.mail.SMTPMailSender;
import org.springframework.stereotype.Service;

import static moim_today.global.constant.MailConstant.*;

@Service
public class AmazonSesService implements MailService {

    private final SMTPMailSender mailSender;

    public AmazonSesService(final SMTPMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendPasswordFindMail(final MailSendRequest mailSendRequest, final String passwordToken) {
        mailSender.send(mailSendRequest, PASSWORD_FIND_MAIL.value(), passwordToken);
    }

    @Override
    public void sendEmailCertificationMail(final MailSendRequest mailSendRequest, final String certificationToken) {
        mailSender.send(mailSendRequest, EMAIL_CERTIFICATION_MAIL.value(), certificationToken);
    }

    @Override
    public void sendUserInquiryResponseMail(final MailSendRequest mailSendRequest, final String responseContent) {
        mailSender.send(mailSendRequest, USER_INQUIRY_RESPONSE_MAIL.value(), responseContent);
    }
}
