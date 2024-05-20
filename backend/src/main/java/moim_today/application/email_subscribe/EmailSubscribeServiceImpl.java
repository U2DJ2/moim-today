package moim_today.application.email_subscribe;

import moim_today.implement.email_subscribe.EmailSubscribeUpdater;
import org.springframework.stereotype.Service;

@Service
public class EmailSubscribeServiceImpl implements EmailSubscribeService {

    private final EmailSubscribeUpdater emailSubscribeUpdater;

    public EmailSubscribeServiceImpl(final EmailSubscribeUpdater emailSubscribeUpdater) {
        this.emailSubscribeUpdater = emailSubscribeUpdater;
    }

    @Override
    public void updateSubscribeStatus(final long memberId, final boolean subscribedStatus) {
        emailSubscribeUpdater.updateSubscribeStatus(memberId, subscribedStatus);
    }
}
