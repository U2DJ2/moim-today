package moim_today.implement.certification_token;

import moim_today.domain.certification_token.CertificationToken;
import moim_today.domain.certification_token.CertificationType;
import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.certification_token.CertificationTokenJpaEntity;
import moim_today.persistence.repository.certification_token.CertificationTokenRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static java.time.LocalDateTime.*;

@Implement
public class CertificationTokenAppender {

    private final CertificationTokenRepository certificationTokenRepository;

    public CertificationTokenAppender(final CertificationTokenRepository certificationTokenRepository) {
        this.certificationTokenRepository = certificationTokenRepository;
    }

    @Transactional
    public String createPasswordToken(final String email) {
        Optional<CertificationTokenJpaEntity> optionalEntity = certificationTokenRepository.findByEmail(email);
        CertificationTokenJpaEntity certificationTokenJpaEntity
                = optionalEntity.orElseGet(() -> CertificationTokenJpaEntity.toEntity(email));

        String randomToken = CertificationToken.createCertificationToken();
        certificationTokenJpaEntity.updateToken(randomToken, CertificationType.PASSWORD, now().plusMinutes(10));
        certificationTokenRepository.save(certificationTokenJpaEntity);
        return randomToken;
    }
}