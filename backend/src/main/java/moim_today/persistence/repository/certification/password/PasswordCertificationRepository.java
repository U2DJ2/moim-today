package moim_today.persistence.repository.certification.password;

import moim_today.persistence.entity.certification.password.PasswordCertificationJpaEntity;

import java.util.Optional;

public interface PasswordCertificationRepository {

    void save(final PasswordCertificationJpaEntity certificationToken);

    PasswordCertificationJpaEntity getByEmail(final String email);

    Optional<PasswordCertificationJpaEntity> findByEmail(final String email);

    PasswordCertificationJpaEntity getByCertificationToken(final String certificationToken);

    long count();
}
