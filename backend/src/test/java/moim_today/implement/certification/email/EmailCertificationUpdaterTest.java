package moim_today.implement.certification.email;

import moim_today.global.error.BadRequestException;
import moim_today.global.error.HandleExceptionPage;
import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.certification.email.EmailCertificationJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static moim_today.global.constant.exception.CertificationConstant.CERTIFICATION_EXPIRED_ERROR;
import static moim_today.global.constant.exception.CertificationConstant.CERTIFICATION_TOKEN_NOT_FOUND_ERROR;
import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;


class EmailCertificationUpdaterTest extends ImplementTest {

    @Autowired
    private EmailCertificationUpdater emailCertificationUpdater;

    @DisplayName("해당 토큰에 대한 이메일이 존재하지 않으면 예외가 발생한다.")
    @Test
    void certificationTokenNotFound() {
        // given
        LocalDateTime expiredTime = LocalDateTime.of(2024, 1, 1, 10, 00, 00);

        // when && then
        assertThatThrownBy(() -> emailCertificationUpdater.certifyEmail(WRONG_CERTIFICATION_TOKEN.value(), expiredTime))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(CERTIFICATION_TOKEN_NOT_FOUND_ERROR.message());
    }

    @DisplayName("만료기간이 지났으면 예외가 발생한다.")
    @Test
    void expiredCertificationToken() {
        // given 1
        LocalDateTime expiredTime = LocalDateTime.of(2024, 1, 1, 10, 00, 00);
        LocalDateTime certificationTime = expiredTime.plusMinutes(1);

        // given 2
        EmailCertificationJpaEntity emailCertificationJpaEntity = EmailCertificationJpaEntity.builder()
                .email(EMAIL.value())
                .certificationToken(CERTIFICATION_TOKEN.value())
                .expiredDateTime(expiredTime)
                .build();

        emailCertificationRepository.save(emailCertificationJpaEntity);

        // when && then
        assertThatThrownBy(() -> emailCertificationUpdater.certifyEmail(CERTIFICATION_TOKEN.value(), certificationTime))
                .isInstanceOf(HandleExceptionPage.class);
    }

    @DisplayName("만료기간이 지나지 않았으면 인증 여부를 true로 업데이트한다.")
    @Test
    void certifyEmail() {
        // given 1
        LocalDateTime expiredTime = LocalDateTime.of(2024, 1, 1, 10, 00, 00);
        LocalDateTime certificationTime = expiredTime.minusMinutes(1);

        // given 2
        EmailCertificationJpaEntity emailCertificationJpaEntity = EmailCertificationJpaEntity.builder()
                .email(EMAIL.value())
                .certificationToken(CERTIFICATION_TOKEN.value())
                .expiredDateTime(expiredTime)
                .build();

        emailCertificationRepository.save(emailCertificationJpaEntity);

        // when
        emailCertificationUpdater.certifyEmail(CERTIFICATION_TOKEN.value(), certificationTime);

        // then
        EmailCertificationJpaEntity findEntity = emailCertificationRepository.getById(emailCertificationJpaEntity.getId());
        assertThat(findEntity.isCertificationStatus()).isTrue();
    }
}