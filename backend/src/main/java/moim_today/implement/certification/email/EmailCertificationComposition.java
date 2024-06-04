package moim_today.implement.certification.email;

import moim_today.dto.certification.CompleteEmailCertificationResponse;
import moim_today.global.annotation.Implement;

import java.time.LocalDateTime;


@Implement
public class EmailCertificationComposition {

    private final EmailCertificationAppender emailCertificationAppender;
    private final EmailCertificationFinder emailCertificationFinder;
    private final EmailCertificationUpdater emailCertificationUpdater;

    public EmailCertificationComposition(final EmailCertificationAppender emailCertificationAppender,
                                         final EmailCertificationFinder emailCertificationFinder,
                                         final EmailCertificationUpdater emailCertificationUpdater) {
        this.emailCertificationAppender = emailCertificationAppender;
        this.emailCertificationFinder = emailCertificationFinder;
        this.emailCertificationUpdater = emailCertificationUpdater;
    }

    public String createEmailToken(final String email, final LocalDateTime expiredDateTime) {
        return emailCertificationAppender.createEmailToken(email, expiredDateTime);
    }

    public CompleteEmailCertificationResponse completeCertification(final String email) {
        return emailCertificationFinder.completeCertification(email);
    }

    public void certifyEmail(final String certificationToken, final LocalDateTime now) {
        emailCertificationUpdater.certifyEmail(certificationToken, now);
    }
}
