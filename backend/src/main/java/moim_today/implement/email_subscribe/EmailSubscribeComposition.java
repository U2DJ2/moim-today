package moim_today.implement.email_subscribe;

import moim_today.global.annotation.Implement;


@Implement
public class EmailSubscribeComposition {

    private final EmailSubscribeAppender emailSubscribeAppender;
    private final EmailSubscriptionFinder emailSubscriptionFinder;
    private final EmailSubscribeUpdater emailSubscribeUpdater;

    public EmailSubscribeComposition(final EmailSubscribeAppender emailSubscribeAppender,
                                     final EmailSubscriptionFinder emailSubscriptionFinder,
                                     final EmailSubscribeUpdater emailSubscribeUpdater) {
        this.emailSubscribeAppender = emailSubscribeAppender;
        this.emailSubscriptionFinder = emailSubscriptionFinder;
        this.emailSubscribeUpdater = emailSubscribeUpdater;
    }

    public void saveEmailSubscription(final long memberId, final boolean subscribeStatus) {
        emailSubscribeAppender.saveEmailSubscription(memberId, subscribeStatus);
    }

    public boolean getSubscriptionStatus(final long memberId) {
        return emailSubscriptionFinder.getSubscriptionStatus(memberId);
    }

    public void updateSubscribeStatus(final long memberId, final boolean subscribeStatus) {
        emailSubscribeUpdater.updateSubscribeStatus(memberId, subscribeStatus);
    }
}
