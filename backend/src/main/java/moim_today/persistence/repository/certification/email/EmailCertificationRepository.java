package moim_today.persistence.repository.certification.email;

import moim_today.persistence.entity.certification.email.EmailCertificationJpaEntity;

import java.util.Optional;

public interface EmailCertificationRepository {

    void save(final EmailCertificationJpaEntity emailCertificationJpaEntity);

    EmailCertificationJpaEntity getByCertificationToken(final String certificationToken);

    Optional<EmailCertificationJpaEntity> findByEmail(final String email);

    long count();
}
