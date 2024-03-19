package booki_today.persistence.repository.email_certification_number;

import booki_today.persistence.entity.email_certification_number.EmailCertificationNumberJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailCertificationJpaRepository extends JpaRepository<EmailCertificationNumberJpaEntity, Long> {
}
