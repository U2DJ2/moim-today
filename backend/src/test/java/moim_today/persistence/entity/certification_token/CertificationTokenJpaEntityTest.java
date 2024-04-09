package moim_today.persistence.entity.certification_token;

import moim_today.domain.certification_token.CertificationType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class CertificationTokenJpaEntityTest {

    @DisplayName("새로운 인증 토큰으로 업데이트한다.")
    @Test
    void updateToken() {
        // given
        CertificationTokenJpaEntity certificationTokenJpaEntity = CertificationTokenJpaEntity.toEntity(EMAIL.value());
        String newToken = "newToken";

        // when
        certificationTokenJpaEntity.updateToken(newToken, CertificationType.PASSWORD, LocalDateTime.now());

        // then
        assertThat(certificationTokenJpaEntity.getCertificationToken()).isEqualTo(newToken);
    }
}