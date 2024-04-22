package moim_today.application.mail;

import moim_today.dto.mail.MailSendRequest;

public interface MailService {

    void sendPasswordFindMail(final MailSendRequest mailSendRequest, final String passwordToken);

    void sendEmailCertificationMail(final MailSendRequest mailSendRequest, final String certificationToken);
}
