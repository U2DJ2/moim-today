package moim_today.implement.email_subscribe;

import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.email_subscribe.EmailSubscribeJpaEntity;
import moim_today.persistence.repository.email_subscribe.EmailSubscribeRepository;
import org.springframework.transaction.annotation.Transactional;

@Implement
public class EmailSubscribeAppender {

    private final EmailSubscribeRepository emailSubscribeRepository;

    public EmailSubscribeAppender(final EmailSubscribeRepository emailSubscribeRepository) {
        this.emailSubscribeRepository = emailSubscribeRepository;
    }

    @Transactional
    public void saveEmailSubscription(final long memberId, final boolean subscribeStatus) {
        EmailSubscribeJpaEntity emailSubscribeJpaEntity = EmailSubscribeJpaEntity.toEntity(memberId, subscribeStatus);
        emailSubscribeRepository.save(emailSubscribeJpaEntity);
    }
}
