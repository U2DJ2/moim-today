package moim_today.application.certification;

import moim_today.application.mail.MailService;
import moim_today.dto.mail.MailSendRequest;
import moim_today.implement.certification.email.EmailCertificationAppender;
import moim_today.implement.certification.password.PasswordCertificationAppender;
import moim_today.implement.member.MemberFinder;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.time.LocalDateTime.now;
import static moim_today.global.constant.MailConstant.EMAIL_CERTIFICATION_SUBJECT;
import static moim_today.global.constant.MailConstant.PASSWORD_FIND_SUBJECT;
import static moim_today.global.constant.TimeConstant.TEN_MINUTES;

@Service
public class CertificationServiceImpl implements CertificationService {

    private final PasswordCertificationAppender passwordCertificationAppender;
    private final EmailCertificationAppender emailCertificationAppender;
    private final MemberFinder memberFinder;
    private final MailService mailService;

    public CertificationServiceImpl(final PasswordCertificationAppender passwordCertificationAppender,
                                    final EmailCertificationAppender emailCertificationAppender,
                                    final MemberFinder memberFinder, final MailService mailService) {
        this.passwordCertificationAppender = passwordCertificationAppender;
        this.emailCertificationAppender = emailCertificationAppender;
        this.memberFinder = memberFinder;
        this.mailService = mailService;
    }

    @Override
    public void createPasswordToken(final String email) {
        memberFinder.validateEmailExists(email);
        String passwordToken = passwordCertificationAppender.createPasswordToken(email);
        MailSendRequest mailSendRequest = MailSendRequest.of(PASSWORD_FIND_SUBJECT.value(), List.of(email));
        mailService.sendPasswordFindMail(mailSendRequest, passwordToken);
    }

    @Override
    public void certifyEmail(final String email) {
        emailCertificationAppender.save(email, now().plusMinutes(TEN_MINUTES.time()));
        MailSendRequest mailSendRequest = MailSendRequest.of(EMAIL_CERTIFICATION_SUBJECT.value(), List.of(email));
        mailService.sendEmailCertificationMail(mailSendRequest);
    }
}
