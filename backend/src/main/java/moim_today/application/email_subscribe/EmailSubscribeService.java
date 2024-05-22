package moim_today.application.email_subscribe;

import moim_today.dto.mail.EmailSubscriptionResponse;

public interface EmailSubscribeService {

    EmailSubscriptionResponse getSubscriptionStatus(final long memberId);

    void updateSubscribeStatus(final long memberId, final boolean subscribedStatus);
}
