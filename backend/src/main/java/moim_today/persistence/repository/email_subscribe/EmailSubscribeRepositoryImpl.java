package moim_today.persistence.repository.email_subscribe;

import org.springframework.stereotype.Repository;

@Repository
public class EmailSubscribeRepositoryImpl implements EmailSubscribeRepository {

    private final EmailSubscribeJpaRepository emailSubscribeJpaRepository;

    public EmailSubscribeRepositoryImpl(final EmailSubscribeJpaRepository emailSubscribeJpaRepository) {
        this.emailSubscribeJpaRepository = emailSubscribeJpaRepository;
    }
}
