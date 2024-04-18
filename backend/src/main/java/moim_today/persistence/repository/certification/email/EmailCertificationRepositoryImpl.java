package moim_today.persistence.repository.certification.email;

import moim_today.persistence.entity.certification.email.EmailCertificationJpaEntity;
import org.springframework.stereotype.Repository;

@Repository
public class EmailCertificationRepositoryImpl implements EmailCertificationRepository {

    private final EmailCertificationJpaRepository emailCertificationJpaRepository;

    public EmailCertificationRepositoryImpl(final EmailCertificationJpaRepository emailCertificationJpaRepository) {
        this.emailCertificationJpaRepository = emailCertificationJpaRepository;
    }

    @Override
    public void save(final EmailCertificationJpaEntity emailCertificationJpaEntity) {
        emailCertificationJpaRepository.save(emailCertificationJpaEntity);
    }
}
