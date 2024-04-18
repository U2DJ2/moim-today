package moim_today.persistence.repository.certification.email;

import org.springframework.stereotype.Repository;

@Repository
public class EmailCertificationRepositoryImpl implements EmailCertificationRepository {

    private final EmailCertificationJpaRepository emailCertificationJpaRepository;

    public EmailCertificationRepositoryImpl(final EmailCertificationJpaRepository emailCertificationJpaRepository) {
        this.emailCertificationJpaRepository = emailCertificationJpaRepository;
    }
}
