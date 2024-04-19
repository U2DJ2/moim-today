package moim_today.persistence.repository.certification.email;

import moim_today.persistence.entity.certification.email.EmailCertificationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailCertificationJpaRepository extends JpaRepository<EmailCertificationJpaEntity, Long> {

    Optional<EmailCertificationJpaEntity> findByCertificationToken(final String certificationToken);
}
