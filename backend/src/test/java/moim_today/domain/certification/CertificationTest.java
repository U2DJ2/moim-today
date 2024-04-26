package moim_today.domain.certification;

import moim_today.global.error.HandleExceptionPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class CertificationTest {

    @DisplayName("16글자 임의의 랜덤 난수를 생성한다.")
    @Test
    void createCertificationToken() {
        // when
        String certificationToken = Certification.createCertificationToken();

        // then
        assertThat(certificationToken.length()).isEqualTo(16);
    }

    @DisplayName("만료일자가 지나지 않으면 검증에 통과한다.")
    @Test
    void validateExpiredDateTime() {
        // given
        LocalDateTime expiredTime = LocalDateTime.of(2024, 1, 1, 10, 00, 00);

        String randomToken = Certification.createCertificationToken();
        Certification certification = Certification.builder()
                .certificationToken(randomToken)
                .expiredDateTime(expiredTime)
                .build();

        // when && then
        certification.validateExpiredDateTime(expiredTime.minusMinutes(9), MESSAGE.value());
    }

    @DisplayName("만료일자가 지나면 예외가 발생한다.")
    @Test
    void validateExpiredDateTimeFail() {
        // given
        LocalDateTime expiredTime = LocalDateTime.of(2024, 1, 1, 10, 00, 00);

        String randomToken = Certification.createCertificationToken();
        Certification certification = Certification.builder()
                .certificationToken(randomToken)
                .expiredDateTime(expiredTime)
                .build();

        // when && then
        assertThatThrownBy(() -> certification.validateExpiredDateTime(expiredTime.plusMinutes(11), MESSAGE.value()))
                .isInstanceOf(HandleExceptionPage.class);
    }

    @DisplayName("특정 이메일을 파싱하여 @ 뒷부분인 학교 이메일 확장자만 반환한다.")
    @Test
    void parseEmailDomain() {
        // given
        LocalDateTime expiredTime = LocalDateTime.of(2024, 1, 1, 10, 00, 00);
        Certification certification = new Certification(AJOU_EMAIL.value(), CERTIFICATION_TOKEN.value(), expiredTime);

        // when
        String emailDomain = certification.parseEmailDomain();

        // then
        assertThat(emailDomain).isEqualTo(AJOU_EMAIL_DOMAIN.value());
    }
}