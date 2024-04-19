package moim_today.implement.certification.password;

import moim_today.persistence.entity.certification.password.PasswordCertificationJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static moim_today.util.TestConstant.EMAIL;
import static org.assertj.core.api.Assertions.assertThat;

class PasswordCertificationAppenderTest extends ImplementTest {

    @Autowired
    private PasswordCertificationAppender passwordCertificationAppender;

    @DisplayName("해당 이메일에 대한 인증 토큰이 존재하지 않으면 새로운 토큰을 생성한다.")
    @Test
    void createPasswordToken() {
        // given
        LocalDateTime expiredDateTime = LocalDateTime.of(2024, 1, 1, 10, 00, 00);

        // when
        String passwordToken = passwordCertificationAppender.createPasswordToken(EMAIL.value(), expiredDateTime);

        // then
        PasswordCertificationJpaEntity findEntity = passwordCertificationRepository.getByEmail(EMAIL.value());
        assertThat(passwordCertificationRepository.count()).isEqualTo(1);
        assertThat(findEntity.getCertificationToken()).isEqualTo(passwordToken);
        assertThat(findEntity.getExpiredDateTime()).isEqualTo(expiredDateTime);
    }

    @DisplayName("해당 이메일에 대한 인증 토큰이 존재하면 인증 토큰을 업데이트한다.")
    @Test
    void updatePasswordToken() {
        // given
        LocalDateTime expiredDateTime = LocalDateTime.of(2024, 1, 1, 10, 00, 00);

        PasswordCertificationJpaEntity passwordCertificationJpaEntity = PasswordCertificationJpaEntity.builder()
                .email(EMAIL.value())
                .build();

        passwordCertificationRepository.save(passwordCertificationJpaEntity);

        // when
        String passwordToken = passwordCertificationAppender.createPasswordToken(EMAIL.value(), expiredDateTime);

        // then
        PasswordCertificationJpaEntity findEntity = passwordCertificationRepository.getByEmail(EMAIL.value());
        assertThat(passwordCertificationRepository.count()).isEqualTo(1);
        assertThat(findEntity.getCertificationToken()).isEqualTo(passwordToken);
        assertThat(findEntity.getExpiredDateTime()).isEqualTo(expiredDateTime);
    }
}