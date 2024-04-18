package moim_today.application.certification;

import moim_today.application.mail.MailService;
import moim_today.dto.mail.MailSendRequest;
import moim_today.implement.certification.password.PasswordCertificationAppender;
import moim_today.implement.member.MemberFinder;
import org.springframework.stereotype.Service;

import java.util.List;

import static moim_today.global.constant.MailConstant.PASSWORD_FIND_SUBJECT;

@Service
public class CertificationServiceImpl implements CertificationService {

    private final PasswordCertificationAppender passwordCertificationAppender;
    private final MemberFinder memberFinder;
    private final MailService mailService;

    public CertificationServiceImpl(final PasswordCertificationAppender passwordCertificationAppender,
                                    final MemberFinder memberFinder, final MailService mailService) {
        this.passwordCertificationAppender = passwordCertificationAppender;
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

    }
}
