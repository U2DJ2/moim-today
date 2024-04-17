package moim_today.application.mail;

import moim_today.dto.mail.MailSendRequest;
import moim_today.dto.mail.MailValidRequest;

public interface MailService {

    void sendPasswordFindMail(final MailSendRequest mailSendRequest, final String passwordToken);

    void sendValidateMail(final MailValidRequest mailValidRequest);


}
