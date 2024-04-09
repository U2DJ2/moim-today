package moim_today.persistence.repository.certification_token;

import moim_today.persistence.entity.certification_token.CertificationTokenJpaEntity;

import java.util.Optional;

public interface CertificationTokenRepository {

    void save(final CertificationTokenJpaEntity certificationToken);

    CertificationTokenJpaEntity getByEmail(final String email);

    Optional<CertificationTokenJpaEntity> findByEmail(final String email);

    CertificationTokenJpaEntity getByCertificationToken(final String certificationToken);

    long count();
}
