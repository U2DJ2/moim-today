package moim_today.persistence.repository.email_certification_token;

import org.springframework.stereotype.Repository;

@Repository
public class EmailCertificationTokenRepositoryImpl implements EmailCertificationTokenRepository {

    private final EmailCertificationTokenJpaRepository emailCertificationTokenJpaRepository;

    public EmailCertificationTokenRepositoryImpl(final EmailCertificationTokenJpaRepository emailCertificationTokenJpaRepository) {
        this.emailCertificationTokenJpaRepository = emailCertificationTokenJpaRepository;
    }
}
