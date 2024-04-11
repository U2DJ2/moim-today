package moim_today.implement.certification_token;

import moim_today.domain.certification_token.CertificationType;
import moim_today.persistence.entity.certification_token.CertificationTokenJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static moim_today.util.TestConstant.EMAIL;
import static org.assertj.core.api.Assertions.assertThat;

class CertificationTokenAppenderTest extends ImplementTest {

    @Autowired
    private CertificationTokenAppender certificationTokenAppender;

    @DisplayName("해당 이메일에 대한 인증 토큰이 존재하지 않으면 새로운 토큰을 생성한다.")
    @Test
    void createPasswordToken() {
        // when
        String passwordToken = certificationTokenAppender.createPasswordToken(EMAIL.value());

        // then
        CertificationTokenJpaEntity findEntity = certificationTokenRepository.getByEmail(EMAIL.value());
        assertThat(certificationTokenRepository.count()).isEqualTo(1);
        assertThat(findEntity.getCertificationToken()).isEqualTo(passwordToken);
        assertThat(findEntity.getCertificationType()).isEqualTo(CertificationType.PASSWORD);
    }

    @DisplayName("해당 이메일에 대한 인증 토큰이 존재하면 인증 토큰을 업데이트한다.")
    @Test
    void updatePasswordToken() {
        // given
        CertificationTokenJpaEntity certificationTokenJpaEntity = CertificationTokenJpaEntity.builder()
                .email(EMAIL.value())
                .build();

        certificationTokenRepository.save(certificationTokenJpaEntity);

        // when
        String passwordToken = certificationTokenAppender.createPasswordToken(EMAIL.value());

        // then
        CertificationTokenJpaEntity findEntity = certificationTokenRepository.getByEmail(EMAIL.value());
        assertThat(certificationTokenRepository.count()).isEqualTo(1);
        assertThat(findEntity.getCertificationToken()).isEqualTo(passwordToken);
        assertThat(findEntity.getCertificationType()).isEqualTo(CertificationType.PASSWORD);
    }
}