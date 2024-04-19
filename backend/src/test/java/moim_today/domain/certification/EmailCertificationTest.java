package moim_today.domain.certification;

import moim_today.global.error.BadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static moim_today.global.constant.exception.CertificationConstant.EMAIL_NOT_YET_CERTIFICATION_ERROR;
import static moim_today.util.TestConstant.AJOU_EMAIL;
import static moim_today.util.TestConstant.CERTIFICATION_TOKEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmailCertificationTest {

    @DisplayName("인증 여부가 false 이면 예외가 발생한다.")
    @Test
    void validateCertificationStatusFail() {
        // given
        LocalDateTime expiredTime = LocalDateTime.of(2024, 1, 1, 10, 00, 00);
        Certification certification = new Certification(AJOU_EMAIL.value(), CERTIFICATION_TOKEN.value(), expiredTime);
        EmailCertification emailCertification = EmailCertification.toDomain(certification, false);

        // when && then
        assertThatThrownBy(emailCertification::validateCertificationStatus)
                .isInstanceOf(BadRequestException.class)
                .hasMessage(EMAIL_NOT_YET_CERTIFICATION_ERROR.message());
    }

    @DisplayName("인증 여부가 true 이면 검증을 통과한다.")
    @Test
    void validateCertificationStatus() {
        // given
        LocalDateTime expiredTime = LocalDateTime.of(2024, 1, 1, 10, 00, 00);
        Certification certification = new Certification(AJOU_EMAIL.value(), CERTIFICATION_TOKEN.value(), expiredTime);
        EmailCertification emailCertification = EmailCertification.toDomain(certification, true);

        // when && then
        emailCertification.validateCertificationStatus();
    }

    @DisplayName("특정 이메일을 파싱하여 @ 뒷부분인 학교 이메일 확장자만 반환한다.")
    @Test
    void parseEmailDomain() {
        // given
        LocalDateTime expiredTime = LocalDateTime.of(2024, 1, 1, 10, 00, 00);
        Certification certification = new Certification(AJOU_EMAIL.value(), CERTIFICATION_TOKEN.value(), expiredTime);
        EmailCertification emailCertification = EmailCertification.toDomain(certification, true);

        // when
        String emailDomain = emailCertification.parseEmailDomain();

        // then
        assertThat(emailDomain).isEqualTo("ajou.ac.kr");
    }
}