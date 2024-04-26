package moim_today.persistence.entity.certification_token;

import moim_today.persistence.entity.certification.password.PasswordCertificationJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class PasswordCertificationJpaEntityTest {

    @DisplayName("새로운 인증 토큰으로 업데이트한다.")
    @Test
    void updateToken() {
        // given
        PasswordCertificationJpaEntity passwordCertificationJpaEntity = PasswordCertificationJpaEntity.builder()
                .certificationToken(CERTIFICATION_TOKEN.value())
                .build();

        String newToken = NEW_CERTIFICATION_TOKEN.value();

        // when
        passwordCertificationJpaEntity.updateToken(newToken, LocalDateTime.now());

        // then
        assertThat(passwordCertificationJpaEntity.getCertificationToken()).isEqualTo(newToken);
    }
}