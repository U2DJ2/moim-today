package moim_today.application.certification_token;

import moim_today.application.mail.MailService;
import moim_today.dto.mail.MailSendRequest;
import moim_today.implement.certification_token.CertificationTokenAppender;
import org.springframework.stereotype.Service;

import java.util.List;

import static moim_today.global.constant.MailConstant.*;

@Service
public class CertificationTokenTokenServiceImpl implements CertificationTokenService {

    private final CertificationTokenAppender certificationTokenAppender;
    private final MailService mailService;

    public CertificationTokenTokenServiceImpl(final CertificationTokenAppender certificationTokenAppender,
                                              final MailService mailService) {
        this.certificationTokenAppender = certificationTokenAppender;
        this.mailService = mailService;
    }

    public void createPasswordToken(final String email) {
        String passwordToken = certificationTokenAppender.createPasswordToken(email);
        MailSendRequest mailSendRequest = MailSendRequest.of(PASSWORD_FIND_SUBJECT.value(), List.of(email));
        mailService.sendPasswordFindMail(mailSendRequest, passwordToken);
    }
}