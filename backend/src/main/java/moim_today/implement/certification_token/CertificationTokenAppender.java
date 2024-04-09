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
        String passwordToken = CertificationToken.createCertificationToken();

        if (optionalEntity.isPresent()) {
            updatePasswordToken(optionalEntity.get(), passwordToken);
        } else {
            createPasswordToken(email, passwordToken);
        }

        return passwordToken;
    }

    private void updatePasswordToken(final CertificationTokenJpaEntity certificationTokenJpaEntity,
                                     final String passwordToken) {
        certificationTokenJpaEntity.updateToken(passwordToken, CertificationType.PASSWORD, now().plusMinutes(10));
    }

    private void createPasswordToken(final String email, final String randomToken) {
        CertificationTokenJpaEntity certificationTokenJpaEntity =
                CertificationTokenJpaEntity.toEntity(
                        email, randomToken, CertificationType.PASSWORD, now().plusMinutes(10)
                );

        certificationTokenRepository.save(certificationTokenJpaEntity);
    }
}