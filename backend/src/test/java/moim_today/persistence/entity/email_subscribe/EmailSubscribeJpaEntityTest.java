package moim_today.persistence.entity.email_subscribe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class EmailSubscribeJpaEntityTest {

    @DisplayName("이메일 수신 여부 설정을 변경한다.")
    @Test
    void updateSubscribeStatus() {
        // given
        EmailSubscribeJpaEntity emailSubscribeJpaEntity = EmailSubscribeJpaEntity.builder()
                .subscribeStatus(true)
                .build();

        // when
        emailSubscribeJpaEntity.updateSubscribeStatus(false);

        // then
        assertThat(emailSubscribeJpaEntity.isSubscribeStatus()).isFalse();
    }
}