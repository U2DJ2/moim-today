package moim_today.implement.email_subscribe;

import moim_today.persistence.entity.email_subscribe.EmailSubscribeJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class EmailSubscriptionFinderTest extends ImplementTest {

    @Autowired
    private EmailSubscriptionFinder emailSubscriptionFinder;

    @DisplayName("해당 회원의 이메일 수신 여부를 조회한다.")
    @Test
    void getSubscriptionStatus() {
        // given
        long memberId = MEMBER_ID.longValue();

        EmailSubscribeJpaEntity emailSubscribeJpaEntity = EmailSubscribeJpaEntity.builder()
                .memberId(memberId)
                .subscribeStatus(true)
                .build();

        emailSubscribeRepository.save(emailSubscribeJpaEntity);

        // when
        boolean subscriptionStatus = emailSubscriptionFinder.getSubscriptionStatus(memberId);

        // then
        assertThat(subscriptionStatus).isTrue();
    }
}