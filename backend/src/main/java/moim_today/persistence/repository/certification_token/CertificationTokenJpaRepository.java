package moim_today.persistence.repository.certification_token;

import moim_today.persistence.entity.certification_token.CertificationTokenJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CertificationTokenJpaRepository extends JpaRepository<CertificationTokenJpaEntity, Long> {

    Optional<CertificationTokenJpaEntity> findByEmail(final String email);

    Optional<CertificationTokenJpaEntity> findByCertificationToken(final String certificationToken);
}