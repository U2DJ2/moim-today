package moim_today.persistence.entity.certification.email;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.assertThat;


class EmailCertificationJpaEntityTest {

    @DisplayName("인증 여부 상태를 업데이트한다.")
    @Test
    void updateCertificationStatus() {
        // given
        EmailCertificationJpaEntity emailCertificationJpaEntity = EmailCertificationJpaEntity.builder()
                .certificationStatus(false)
                .build();

        // when
        emailCertificationJpaEntity.updateCertificationStatus(true);

        // then
        assertThat(emailCertificationJpaEntity.isCertificationStatus()).isTrue();
    }

    @DisplayName("인증 토큰과 만료일자를 같이 업데이트한다.")
    @Test
    void updateToken() {
        // given
        LocalDateTime expiredDateTime = LocalDateTime.of(2024, 1, 1, 10, 00, 00);
        LocalDateTime newExpiredDateTime = expiredDateTime.plusMinutes(10);

        EmailCertificationJpaEntity emailCertificationJpaEntity = EmailCertificationJpaEntity.builder()
                .certificationToken(WRONG_CERTIFICATION_TOKEN.value())
                .expiredDateTime(expiredDateTime)
                .build();

        // when
        emailCertificationJpaEntity.updateToken(CERTIFICATION_TOKEN.value(), expiredDateTime.plusMinutes(10));

        // then
        assertThat(emailCertificationJpaEntity.getCertificationToken()).isEqualTo(CERTIFICATION_TOKEN.value());
        assertThat(emailCertificationJpaEntity.getExpiredDateTime()).isEqualTo(newExpiredDateTime);
    }
}