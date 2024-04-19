package moim_today.persistence.repository.certification.email;

import moim_today.persistence.entity.certification.email.EmailCertificationJpaEntity;

public interface EmailCertificationRepository {

    void save(final EmailCertificationJpaEntity emailCertificationJpaEntity);

    EmailCertificationJpaEntity getByCertificationToken(final String certificationToken);
}
