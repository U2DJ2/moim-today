package moim_today.persistence.repository.certification.password;

import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.certification.password.PasswordCertificationJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static moim_today.global.constant.exception.CertificationConstant.CERTIFICATION_TOKEN_NOT_FOUND_ERROR;
import static moim_today.global.constant.exception.MailExceptionConstant.MAIL_CERTIFICATION_TOKEN_NOT_FOUND_ERROR;

@Repository
public class PasswordCertificationRepositoryImpl implements PasswordCertificationRepository {

    private final PasswordCertificationJpaRepository passwordCertificationJpaRepository;

    public PasswordCertificationRepositoryImpl(final PasswordCertificationJpaRepository passwordCertificationJpaRepository) {
        this.passwordCertificationJpaRepository = passwordCertificationJpaRepository;
    }

    @Override
    public void save(final PasswordCertificationJpaEntity certificationToken) {
        passwordCertificationJpaRepository.save(certificationToken);
    }

    @Override
    public PasswordCertificationJpaEntity getByEmail(final String email) {
        return passwordCertificationJpaRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(CERTIFICATION_TOKEN_NOT_FOUND_ERROR.message()));
    }

    @Override
    public Optional<PasswordCertificationJpaEntity> findByEmail(final String email) {
        return passwordCertificationJpaRepository.findByEmail(email);
    }

    @Override
    public PasswordCertificationJpaEntity getByCertificationToken(final String certificationToken) {
        return passwordCertificationJpaRepository.findByCertificationToken(certificationToken)
                .orElseThrow(() -> new NotFoundException(MAIL_CERTIFICATION_TOKEN_NOT_FOUND_ERROR.message()));
    }

    @Override
    public long count() {
        return passwordCertificationJpaRepository.count();
    }
}
