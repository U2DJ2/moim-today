package moim_today.application.certification.password;

import moim_today.application.mail.MailService;
import moim_today.dto.mail.MailSendRequest;
import moim_today.global.constant.TimeConstant;
import moim_today.implement.certification.password.PasswordCertificationComposition;
import moim_today.implement.member.MemberFinder;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.time.LocalDateTime.*;
import static moim_today.global.constant.MailConstant.PASSWORD_FIND_SUBJECT;

@Service
public class PasswordCertificationServiceImpl implements PasswordCertificationService {

    private final PasswordCertificationComposition passwordCertificationComposition;
    private final MemberFinder memberFinder;
    private final MailService mailService;

    public PasswordCertificationServiceImpl(final PasswordCertificationComposition passwordCertificationComposition,
                                            final MemberFinder memberFinder,
                                            final MailService mailService) {
        this.passwordCertificationComposition = passwordCertificationComposition;
        this.memberFinder = memberFinder;
        this.mailService = mailService;
    }

    @Override
    public void sendPasswordToken(final String email) {
        memberFinder.validateEmailExists(email);
        String passwordToken = passwordCertificationComposition.createPasswordToken(
                email, now().plusMinutes(TimeConstant.TEN_MINUTES.time())
        );

        MailSendRequest mailSendRequest = MailSendRequest.of(PASSWORD_FIND_SUBJECT.value(), List.of(email));
        mailService.sendPasswordFindMail(mailSendRequest, passwordToken);
    }
}
