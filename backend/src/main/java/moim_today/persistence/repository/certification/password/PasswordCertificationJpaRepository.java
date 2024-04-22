package moim_today.persistence.repository.certification.password;

import moim_today.persistence.entity.certification.password.PasswordCertificationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordCertificationJpaRepository extends JpaRepository<PasswordCertificationJpaEntity, Long> {

    Optional<PasswordCertificationJpaEntity> findByEmail(final String email);

    Optional<PasswordCertificationJpaEntity> findByCertificationToken(final String certificationToken);
}