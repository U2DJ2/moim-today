package moim_today.implement.certification.email;

import moim_today.persistence.entity.certification.email.EmailCertificationJpaEntity;
import moim_today.util.ImplementTest;
import moim_today.util.TestConstant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class EmailCertificationAppenderTest extends ImplementTest {

    @Autowired
    private EmailCertificationAppender emailCertificationAppender;

    @DisplayName("해당 이메일에 대한 만료시간을 설정하여 이메일 인증 정보를 생성한다.")
    @Test
    void createEmailToken() {
        // given
        LocalDateTime expiredTime = LocalDateTime.of(2024, 1, 1, 10, 00, 00);

        // when
        String certificationToken = emailCertificationAppender.createEmailToken(TestConstant.EMAIL.value(), expiredTime);

        // then
        EmailCertificationJpaEntity findEntity = emailCertificationRepository.getByCertificationToken(certificationToken);
        assertThat(emailCertificationRepository.count()).isEqualTo(1);
        assertThat(findEntity.getCertificationToken()).isEqualTo(certificationToken);
        assertThat(findEntity.getExpiredDateTime()).isEqualTo(expiredTime);
    }
}