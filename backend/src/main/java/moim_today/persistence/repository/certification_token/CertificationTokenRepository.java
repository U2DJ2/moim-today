package moim_today.persistence.repository.certification_token;

import moim_today.domain.certification_token.CertificationToken;
import moim_today.persistence.entity.certification_token.CertificationTokenJpaEntity;

import java.util.Optional;

public interface CertificationTokenRepository {

    Optional<CertificationTokenJpaEntity> findByEmail(final String email);

    CertificationTokenJpaEntity getByCertificationToken(final String certificationToken);
}
