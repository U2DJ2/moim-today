package moim_today.persistence.repository.certification.email;

import moim_today.persistence.entity.certification.email.EmailCertificationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailCertificationJpaRepository extends JpaRepository<EmailCertificationJpaEntity, Long> {
}
