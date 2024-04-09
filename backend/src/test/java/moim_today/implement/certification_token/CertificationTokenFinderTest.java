package moim_today.implement.certification_token;

import moim_today.persistence.entity.certification_token.CertificationTokenJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class CertificationTokenFinderTest extends ImplementTest {

    @Autowired
    private CertificationTokenFinder certificationTokenFinder;

    @DisplayName("인증 토큰에 해당하는 인증 토큰 엔티티를 가져온다.")
    @Test
    void getByCertificationToken() {
        // given
        String certificationToken = CERTIFICATION_TOKEN.value();
        CertificationTokenJpaEntity certificationTokenJpaEntity = CertificationTokenJpaEntity.builder()
                .certificationToken(certificationToken)
                .build();

        certificationTokenRepository.save(certificationTokenJpaEntity);

        // when
        CertificationTokenJpaEntity findEntity = certificationTokenFinder.getByCertificationToken(certificationToken);

        // then
        assertThat(findEntity).isNotNull();
        assertThat(findEntity.getCertificationToken()).isEqualTo(certificationToken);
    }
}