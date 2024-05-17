package moim_today.domain.moim;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ViewedMoimTest {

    @DisplayName("만료시간이 지났으면 ture를 반환한다.")
    @Test
    void isExpiredTrueTest() {
        //given
        ViewedMoim viewedMoim = ViewedMoim.builder()
                .expiredTime(LocalDateTime.now().minusDays(1))
                .build();

        //when
        boolean isExpired = viewedMoim.checkExpired();

        //then
        assertThat(isExpired).isTrue();
    }

    @DisplayName("만료시간이 지나지 않았으면 false를 반환한다.")
    @Test
    void isExpiredFalseTest() {
        //given
        ViewedMoim viewedMoim = ViewedMoim.builder()
                .expiredTime(LocalDateTime.now().plusDays(1))
                .build();

        //when
        boolean isExpired = viewedMoim.checkExpired();

        //then
        assertThat(isExpired).isFalse();
    }
}