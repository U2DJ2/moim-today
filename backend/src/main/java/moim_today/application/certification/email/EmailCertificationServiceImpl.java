package moim_today.application.certification.email;

import moim_today.application.mail.MailService;
import moim_today.dto.certification.CompleteEmailCertificationResponse;
import moim_today.dto.mail.MailSendRequest;
import moim_today.implement.certification.email.EmailCertificationComposition;
import moim_today.implement.member.MemberFinder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDateTime.now;
import static moim_today.global.constant.MailConstant.EMAIL_CERTIFICATION_SUBJECT;
import static moim_today.global.constant.TimeConstant.TEN_MINUTES;

@Service
public class EmailCertificationServiceImpl implements EmailCertificationService {

    private final EmailCertificationComposition emailCertificationComposition;
    private final MemberFinder memberFinder;
    private final MailService mailService;

    public EmailCertificationServiceImpl(final EmailCertificationComposition emailCertificationComposition,
                                         final MemberFinder memberFinder,
                                         final MailService mailService) {
        this.emailCertificationComposition = emailCertificationComposition;
        this.memberFinder = memberFinder;
        this.mailService = mailService;
    }

    @Override
    public void sendCertificationEmail(final String email) {
        memberFinder.validateAlreadyExists(email);

        String emailToken = emailCertificationComposition.createEmailToken(
                email, now().plusMinutes(TEN_MINUTES.time())
        );

        MailSendRequest mailSendRequest = MailSendRequest.of(EMAIL_CERTIFICATION_SUBJECT.value(), List.of(email));
        mailService.sendEmailCertificationMail(mailSendRequest, emailToken);
    }

    @Override
    public void certifyEmail(final String certificationToken) {
        emailCertificationComposition.certifyEmail(certificationToken, LocalDateTime.now());
    }

    @Override
    public CompleteEmailCertificationResponse completeCertification(final String email) {
        return emailCertificationComposition.completeCertification(email);
    }
}
