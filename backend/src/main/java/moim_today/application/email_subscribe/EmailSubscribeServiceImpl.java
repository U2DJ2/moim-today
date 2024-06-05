package moim_today.application.email_subscribe;

import moim_today.dto.mail.EmailSubscriptionResponse;
import moim_today.implement.email_subscribe.EmailSubscribeComposition;
import org.springframework.stereotype.Service;

@Service
public class EmailSubscribeServiceImpl implements EmailSubscribeService {

    private final EmailSubscribeComposition emailSubscribeComposition;

    public EmailSubscribeServiceImpl(final EmailSubscribeComposition emailSubscribeComposition) {
        this.emailSubscribeComposition = emailSubscribeComposition;
    }

    @Override
    public EmailSubscriptionResponse getSubscriptionStatus(final long memberId) {
        boolean subscriptionStatus = emailSubscribeComposition.getSubscriptionStatus(memberId);
        return EmailSubscriptionResponse.of(subscriptionStatus);
    }

    @Override
    public void updateSubscribeStatus(final long memberId, final boolean subscribedStatus) {
        emailSubscribeComposition.updateSubscribeStatus(memberId, subscribedStatus);
    }
}
