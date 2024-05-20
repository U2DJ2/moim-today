package moim_today.application.email_subscribe;

public interface EmailSubscribeService {

    void updateSubscribeStatus(final long memberId, final boolean subscribedStatus);
}
