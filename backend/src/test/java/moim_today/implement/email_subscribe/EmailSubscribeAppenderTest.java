package moim_today.implement.email_subscribe;

import moim_today.persistence.entity.email_subscribe.EmailSubscribeJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class EmailSubscribeAppenderTest extends ImplementTest {

    @Autowired
    private EmailSubscribeAppender emailSubscribeAppender;

    @DisplayName("이메일 수신 여부 정보를 저장한다.")
    @Test
    void saveEmailSubscription() {
        // given
        long memberId = MEMBER_ID.longValue();
        boolean subscriptionStatus = true;

        // when
        emailSubscribeAppender.saveEmailSubscription(memberId, subscriptionStatus);

        // then
        EmailSubscribeJpaEntity findEntity = emailSubscribeRepository.getByMemberId(memberId);

        assertThat(emailSubscribeRepository.count()).isEqualTo(1);
        assertThat(findEntity.isSubscribeStatus()).isTrue();
    }
}