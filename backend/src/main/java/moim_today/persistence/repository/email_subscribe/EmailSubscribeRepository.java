package moim_today.persistence.repository.email_subscribe;

import moim_today.persistence.entity.email_subscribe.EmailSubscribeJpaEntity;


public interface EmailSubscribeRepository {

    EmailSubscribeJpaEntity getByMemberId(final long memberId);
}
