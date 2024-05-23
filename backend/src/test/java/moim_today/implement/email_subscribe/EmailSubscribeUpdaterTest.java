package moim_today.implement.email_subscribe;

import moim_today.persistence.entity.email_subscribe.EmailSubscribeJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;


class EmailSubscribeUpdaterTest extends ImplementTest {

    @Autowired
    private EmailSubscribeUpdater emailSubscribeUpdater;

    @DisplayName("이메일 수신 여부 설정을 변경한다.")
    @Test
    void updateSubscribeStatus() {
        // given
        long memberId = MEMBER_ID.longValue();

        EmailSubscribeJpaEntity emailSubscribeJpaEntity = EmailSubscribeJpaEntity.builder()
                .memberId(memberId)
                .subscribeStatus(true)
                .build();

        emailSubscribeRepository.save(emailSubscribeJpaEntity);

        // when
        emailSubscribeUpdater.updateSubscribeStatus(memberId, false);

        // then
        EmailSubscribeJpaEntity findEntity = emailSubscribeRepository.getById(emailSubscribeJpaEntity.getId());
        assertThat(findEntity.isSubscribeStatus()).isFalse();
    }
}