package moim_today.implement.email_subscribe;

import moim_today.global.annotation.Implement;


@Implement
public class EmailSubscribeComposition {

    private final EmailSubscriptionFinder emailSubscriptionFinder;
    private final EmailSubscribeUpdater emailSubscribeUpdater;

    public EmailSubscribeComposition(final EmailSubscriptionFinder emailSubscriptionFinder,
                                     final EmailSubscribeUpdater emailSubscribeUpdater) {
        this.emailSubscriptionFinder = emailSubscriptionFinder;
        this.emailSubscribeUpdater = emailSubscribeUpdater;
    }

    public boolean getSubscriptionStatus(final long memberId) {
        return emailSubscriptionFinder.getSubscriptionStatus(memberId);
    }

    public void updateSubscribeStatus(final long memberId, final boolean subscribeStatus) {
        emailSubscribeUpdater.updateSubscribeStatus(memberId, subscribeStatus);
    }
}
