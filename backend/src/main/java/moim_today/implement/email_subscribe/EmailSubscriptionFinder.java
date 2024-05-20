package moim_today.implement.email_subscribe;

import moim_today.global.annotation.Implement;
import moim_today.persistence.repository.email_subscribe.EmailSubscribeRepository;
import org.springframework.transaction.annotation.Transactional;

@Implement
public class EmailSubscriptionFinder {

    private final EmailSubscribeRepository emailSubscribeRepository;

    public EmailSubscriptionFinder(final EmailSubscribeRepository emailSubscribeRepository) {
        this.emailSubscribeRepository = emailSubscribeRepository;
    }

    @Transactional(readOnly = true)
    public boolean getSubscriptionStatus(final long memberId) {
        return emailSubscribeRepository.getSubscriptionStatus(memberId);
    }
}
