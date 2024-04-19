package moim_today.application.certification.email;

import moim_today.application.mail.MailService;
import moim_today.dto.mail.MailSendRequest;
import moim_today.implement.certification.email.EmailCertificationAppender;
import moim_today.implement.certification.email.EmailCertificationUpdater;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDateTime.now;
import static moim_today.global.constant.MailConstant.EMAIL_CERTIFICATION_SUBJECT;
import static moim_today.global.constant.TimeConstant.TEN_MINUTES;

@Service
public class EmailCertificationServiceImpl implements EmailCertificationService {

    private final EmailCertificationAppender emailCertificationAppender;
    private final EmailCertificationUpdater emailCertificationUpdater;
    private final MailService mailService;

    public EmailCertificationServiceImpl(final EmailCertificationAppender emailCertificationAppender,
                                         final EmailCertificationUpdater emailCertificationUpdater,
                                         final MailService mailService) {
        this.emailCertificationAppender = emailCertificationAppender;
        this.emailCertificationUpdater = emailCertificationUpdater;
        this.mailService = mailService;
    }

    @Override
    public void sendCertificationEmail(final String email) {
        String emailToken = emailCertificationAppender.createEmailToken(email, now().plusMinutes(TEN_MINUTES.time()));
        MailSendRequest mailSendRequest = MailSendRequest.of(EMAIL_CERTIFICATION_SUBJECT.value(), List.of(email));
        mailService.sendEmailCertificationMail(mailSendRequest, emailToken);
    }

    @Override
    public void certifyEmail(final String certificationToken) {
        emailCertificationUpdater.certifyEmail(certificationToken, LocalDateTime.now());
    }
}
