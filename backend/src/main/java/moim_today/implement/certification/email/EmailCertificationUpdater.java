package moim_today.implement.certification.email;

import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.certification.email.EmailCertificationJpaEntity;
import moim_today.persistence.repository.certification.email.EmailCertificationRepository;
import org.springframework.transaction.annotation.Transactional;

@Implement
public class EmailCertificationUpdater {

    private final EmailCertificationRepository emailCertificationRepository;

    public EmailCertificationUpdater(final EmailCertificationRepository emailCertificationRepository) {
        this.emailCertificationRepository = emailCertificationRepository;
    }

    @Transactional
    public void certifyEmail(final String certificationToken) {
        EmailCertificationJpaEntity emailCertificationJpaEntity
                = emailCertificationRepository.getByCertificationToken(certificationToken);


        emailCertificationJpaEntity.updateCertificationStatus(true);
    }
}
