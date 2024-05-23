package moim_today.application.email_subscribe;

import moim_today.dto.mail.EmailSubscriptionResponse;
import moim_today.implement.email_subscribe.EmailSubscribeUpdater;
import moim_today.implement.email_subscribe.EmailSubscriptionFinder;
import org.springframework.stereotype.Service;

@Service
public class EmailSubscribeServiceImpl implements EmailSubscribeService {

    private final EmailSubscribeUpdater emailSubscribeUpdater;
    private final EmailSubscriptionFinder emailSubscriptionFinder;

    public EmailSubscribeServiceImpl(final EmailSubscribeUpdater emailSubscribeUpdater,
                                     final EmailSubscriptionFinder emailSubscriptionFinder) {
        this.emailSubscribeUpdater = emailSubscribeUpdater;
        this.emailSubscriptionFinder = emailSubscriptionFinder;
    }

    @Override
    public EmailSubscriptionResponse getSubscriptionStatus(final long memberId) {
        boolean subscriptionStatus = emailSubscriptionFinder.getSubscriptionStatus(memberId);
        return EmailSubscriptionResponse.of(subscriptionStatus);
    }

    @Override
    public void updateSubscribeStatus(final long memberId, final boolean subscribedStatus) {
        emailSubscribeUpdater.updateSubscribeStatus(memberId, subscribedStatus);
    }
}
