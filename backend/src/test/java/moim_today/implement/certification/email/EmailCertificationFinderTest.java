package moim_today.implement.certification.email;

import moim_today.dto.certification.CompleteEmailCertificationResponse;
import moim_today.global.error.BadRequestException;
import moim_today.persistence.entity.certification.email.EmailCertificationJpaEntity;
import moim_today.persistence.entity.university.UniversityJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static moim_today.global.constant.exception.CertificationConstant.EMAIL_NOT_YET_CERTIFICATION_ERROR;
import static org.assertj.core.api.Assertions.*;


class EmailCertificationFinderTest extends ImplementTest {

    @Autowired
    private EmailCertificationFinder emailCertificationFinder;

    @DisplayName("해당 이메일이 인증이 완료된 상태가 아니라면 예외가 발생한다.")
    @Test
    void completeCertificationFail() {
        // given 1
        UniversityJpaEntity universityJpaEntity = UniversityJpaEntity.builder()
                .universityName("아주대학교")
                .universityEmail("ajou.ac.kr")
                .build();

        universityRepository.save(universityJpaEntity);

        // given 2
        EmailCertificationJpaEntity emailCertificationJpaEntity = EmailCertificationJpaEntity.builder()
                .email("email@ajou.ac.kr")
                .certificationStatus(false)
                .build();

        emailCertificationRepository.save(emailCertificationJpaEntity);

        // when && then
        assertThatThrownBy(() -> emailCertificationFinder.completeCertification("email@ajou.ac.kr"))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(EMAIL_NOT_YET_CERTIFICATION_ERROR.message());
    }

    @DisplayName("해당 이메일이 인증이 완료된 상태라면 학교 id, 학교 이름을 반환한다.")
    @Test
    void completeCertification() {
        // given 1
        UniversityJpaEntity universityJpaEntity = UniversityJpaEntity.builder()
                .universityName("아주대학교")
                .universityEmail("ajou.ac.kr")
                .build();

        universityRepository.save(universityJpaEntity);

        // given 2
        EmailCertificationJpaEntity emailCertificationJpaEntity = EmailCertificationJpaEntity.builder()
                .email("email@ajou.ac.kr")
                .certificationStatus(true)
                .build();

        emailCertificationRepository.save(emailCertificationJpaEntity);

        // when
        CompleteEmailCertificationResponse completeEmailCertificationResponse =
                emailCertificationFinder.completeCertification("email@ajou.ac.kr");

        // then
        assertThat(completeEmailCertificationResponse.universityName()).isEqualTo("아주대학교");
    }
}