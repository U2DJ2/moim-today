package moim_today.persistence.repository.certification.email;

import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.certification.email.EmailCertificationJpaEntity;
import org.springframework.stereotype.Repository;

import static moim_today.global.constant.exception.CertificationConstant.*;

@Repository
public class EmailCertificationRepositoryImpl implements EmailCertificationRepository {

    private final EmailCertificationJpaRepository emailCertificationJpaRepository;

    public EmailCertificationRepositoryImpl(final EmailCertificationJpaRepository emailCertificationJpaRepository) {
        this.emailCertificationJpaRepository = emailCertificationJpaRepository;
    }

    @Override
    public void save(final EmailCertificationJpaEntity emailCertificationJpaEntity) {
        emailCertificationJpaRepository.save(emailCertificationJpaEntity);
    }

    @Override
    public EmailCertificationJpaEntity getByCertificationToken(final String certificationToken) {
        return emailCertificationJpaRepository.findByCertificationToken(certificationToken)
                .orElseThrow(() -> new NotFoundException(CERTIFICATION_TOKEN_NOT_FOUND_ERROR.message()));
    }
}
