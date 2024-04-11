package booki_today.persistence.repository.email_certification_number;

import org.springframework.stereotype.Repository;

@Repository
public class EmailCertificationNumberRepositoryImpl implements EmailCertificationNumberRepository {

    private final EmailCertificationJpaRepository emailCertificationJpaRepository;

    public EmailCertificationNumberRepositoryImpl(final EmailCertificationJpaRepository emailCertificationJpaRepository) {
        this.emailCertificationJpaRepository = emailCertificationJpaRepository;
    }
}
