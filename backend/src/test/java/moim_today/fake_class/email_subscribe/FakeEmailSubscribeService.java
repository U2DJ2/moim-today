package moim_today.fake_class.email_subscribe;

import moim_today.application.email_subscribe.EmailSubscribeService;
import moim_today.dto.mail.EmailSubscriptionResponse;

public class FakeEmailSubscribeService implements EmailSubscribeService {

    @Override
    public void updateSubscribeStatus(final long memberId, final boolean subscribedStatus) {

    }

    @Override
    public EmailSubscriptionResponse getSubscriptionStatus(final long memberId) {
        return EmailSubscriptionResponse.of(true);
    }
}
