package moim_today.persistence.repository.email_subscribe;

import moim_today.persistence.entity.email_subscribe.EmailSubscribeJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailSubscribeJpaRepository extends JpaRepository<EmailSubscribeJpaEntity, Long> {

    Optional<EmailSubscribeJpaEntity> findByMemberId(final long memberId);
}
