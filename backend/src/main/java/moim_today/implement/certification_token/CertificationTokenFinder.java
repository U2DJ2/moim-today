package moim_today.implement.certification_token;

import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.certification_token.CertificationTokenJpaEntity;
import moim_today.persistence.repository.certification_token.CertificationTokenRepository;
import org.springframework.transaction.annotation.Transactional;

@Implement
public class CertificationTokenFinder {

    private final CertificationTokenRepository certificationTokenRepository;

    public CertificationTokenFinder(final CertificationTokenRepository certificationTokenRepository) {
        this.certificationTokenRepository = certificationTokenRepository;
    }

    @Transactional(readOnly = true)
    public CertificationTokenJpaEntity getByCertificationToken(final String certificationToken) {
        return certificationTokenRepository.getByCertificationToken(certificationToken);
    }
}
