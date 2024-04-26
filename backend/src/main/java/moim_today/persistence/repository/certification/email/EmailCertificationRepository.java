package moim_today.persistence.repository.certification.email;

import moim_today.persistence.entity.certification.email.EmailCertificationJpaEntity;

import java.util.Optional;

public interface EmailCertificationRepository {

    EmailCertificationJpaEntity getById(final long emailCertificationId);

    void save(final EmailCertificationJpaEntity emailCertificationJpaEntity);

    EmailCertificationJpaEntity getByCertificationToken(final String certificationToken);

    EmailCertificationJpaEntity getByEmail(final String email);

    Optional<EmailCertificationJpaEntity> findByEmail(final String email);

    long count();
}
