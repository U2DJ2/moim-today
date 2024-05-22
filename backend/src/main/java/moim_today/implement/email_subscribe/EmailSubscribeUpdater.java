package moim_today.implement.email_subscribe;

import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.email_subscribe.EmailSubscribeJpaEntity;
import moim_today.persistence.repository.email_subscribe.EmailSubscribeRepository;
import org.springframework.transaction.annotation.Transactional;

@Implement
public class EmailSubscribeUpdater {

    private final EmailSubscribeRepository emailSubscribeRepository;

    public EmailSubscribeUpdater(final EmailSubscribeRepository emailSubscribeRepository) {
        this.emailSubscribeRepository = emailSubscribeRepository;
    }

    @Transactional
    public void updateSubscribeStatus(final long memberId, final boolean subscribeStatus) {
        EmailSubscribeJpaEntity emailSubscribeJpaEntity = emailSubscribeRepository.getByMemberId(memberId);
        emailSubscribeJpaEntity.updateSubscribeStatus(subscribeStatus);
    }
}
