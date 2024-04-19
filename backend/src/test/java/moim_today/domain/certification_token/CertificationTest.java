package moim_today.domain.certification_token;

import moim_today.global.error.BadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static moim_today.global.constant.exception.CertificationConstant.CERTIFICATION_EXPIRED_ERROR;
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
        certification.validateExpiredDateTime(expiredTime.minusMinutes(9));
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
        assertThatThrownBy(() -> certification.validateExpiredDateTime(expiredTime.plusMinutes(11)))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(CERTIFICATION_EXPIRED_ERROR.message());
    }
}