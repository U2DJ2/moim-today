package moim_today.persistence.repository.certification_token;

import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.certification_token.CertificationTokenJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static moim_today.global.constant.exception.CertificationConstant.CERTIFICATION_NOT_FOUND_ERROR;
import static moim_today.global.constant.exception.MailExceptionConstant.MAIL_CERTIFICATION_TOKEN_NOT_FOUND_ERROR;

@Repository
public class CertificationTokenRepositoryImpl implements CertificationTokenRepository {

    private final CertificationTokenJpaRepository certificationTokenJpaRepository;

    public CertificationTokenRepositoryImpl(final CertificationTokenJpaRepository certificationTokenJpaRepository) {
        this.certificationTokenJpaRepository = certificationTokenJpaRepository;
    }

    @Override
    public void save(final CertificationTokenJpaEntity certificationToken) {
        certificationTokenJpaRepository.save(certificationToken);
    }

    @Override
    public CertificationTokenJpaEntity getByEmail(final String email) {
        return certificationTokenJpaRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(CERTIFICATION_NOT_FOUND_ERROR.message()));
    }

    @Override
    public Optional<CertificationTokenJpaEntity> findByEmail(final String email) {
        return certificationTokenJpaRepository.findByEmail(email);
    }

    @Override
    public CertificationTokenJpaEntity getByCertificationToken(final String certificationToken) {
        return certificationTokenJpaRepository.findByCertificationToken(certificationToken)
                .orElseThrow(() -> new NotFoundException(MAIL_CERTIFICATION_TOKEN_NOT_FOUND_ERROR.message()));
    }

    @Override
    public long count() {
        return certificationTokenJpaRepository.count();
    }
}
