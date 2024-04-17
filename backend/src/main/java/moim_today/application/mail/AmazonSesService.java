package moim_today.application.mail;

import moim_today.dto.mail.MailSendRequest;
import moim_today.dto.mail.MailValidRequest;
import moim_today.implement.mail.MailSender;
import org.springframework.stereotype.Service;

import static moim_today.global.constant.MailConstant.PASSWORD_FIND_MAIL;

@Service
public class AmazonSesService implements MailService {

    private final MailSender mailSender;

    public AmazonSesService(final MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendPasswordFindMail(final MailSendRequest mailSendRequest, final String passwordToken) {
        mailSender.send(mailSendRequest, PASSWORD_FIND_MAIL.value(), passwordToken);
    }

//    ToDo: 이메일 인증번호 날리는 로직 구현 미완성
    @Override
    public void sendValidateMail(final MailValidRequest mailValidRequest) {
        return;
    }
}
