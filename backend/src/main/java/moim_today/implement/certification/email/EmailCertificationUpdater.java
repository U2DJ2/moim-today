package moim_today.implement.certification.email;

import moim_today.domain.certification.Certification;
import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.certification.email.EmailCertificationJpaEntity;
import moim_today.persistence.repository.certification.email.EmailCertificationRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static moim_today.global.constant.MailConstant.*;

@Implement
public class EmailCertificationUpdater {

    private final EmailCertificationRepository emailCertificationRepository;

    public EmailCertificationUpdater(final EmailCertificationRepository emailCertificationRepository) {
        this.emailCertificationRepository = emailCertificationRepository;
    }

    @Transactional
    public void certifyEmail(final String certificationToken, final LocalDateTime now) {
        EmailCertificationJpaEntity emailCertificationJpaEntity
                = emailCertificationRepository.getByCertificationToken(certificationToken);

        Certification certification = Certification.toDomain(emailCertificationJpaEntity);
        certification.validateExpiredDateTime(now, EMAIL_CERTIFICATION_FAIL.value());
        emailCertificationJpaEntity.updateCertificationStatus(true);
    }
}
