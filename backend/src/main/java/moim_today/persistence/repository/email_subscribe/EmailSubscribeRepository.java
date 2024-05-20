package moim_today.persistence.repository.email_subscribe;

import moim_today.persistence.entity.email_subscribe.EmailSubscribeJpaEntity;


public interface EmailSubscribeRepository {

    EmailSubscribeJpaEntity getById(final long emailSubscribeId);

    EmailSubscribeJpaEntity getByMemberId(final long memberId);

    EmailSubscribeJpaEntity save(final EmailSubscribeJpaEntity emailSubscribeJpaEntity);
}
