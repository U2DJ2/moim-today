package moim_today.implement.certification;

import moim_today.implement.certification.password.PasswordCertificationFinder;
import moim_today.persistence.entity.certification.password.PasswordCertificationJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class PasswordCertificationFinderTest extends ImplementTest {

    @Autowired
    private PasswordCertificationFinder passwordCertificationFinder;

    @DisplayName("인증 토큰에 해당하는 인증 토큰 엔티티를 가져온다.")
    @Test
    void getByCertificationToken() {
        // given
        String certificationToken = CERTIFICATION_TOKEN.value();
        PasswordCertificationJpaEntity passwordCertificationJpaEntity = PasswordCertificationJpaEntity.builder()
                .certificationToken(certificationToken)
                .build();

        passwordCertificationRepository.save(passwordCertificationJpaEntity);

        // when
        PasswordCertificationJpaEntity findEntity = passwordCertificationFinder.getByCertificationToken(certificationToken);

        // then
        assertThat(findEntity).isNotNull();
        assertThat(findEntity.getCertificationToken()).isEqualTo(certificationToken);
    }
}