package moim_today.implement.certification.email;

import moim_today.domain.certification_token.Certification;
import moim_today.persistence.entity.certification.email.EmailCertificationJpaEntity;
import moim_today.persistence.repository.certification.email.EmailCertificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class EmailCertificationAppender {

    private final EmailCertificationRepository emailCertificationRepository;

    public EmailCertificationAppender(final EmailCertificationRepository emailCertificationRepository) {
        this.emailCertificationRepository = emailCertificationRepository;
    }

    @Transactional
    public String save(final String email, final LocalDateTime expiredDateTime) {
        String certificationToken = Certification.createCertificationToken();
        EmailCertificationJpaEntity emailCertificationJpaEntity
                = EmailCertificationJpaEntity.toEntity(email, certificationToken, expiredDateTime);
        emailCertificationRepository.save(emailCertificationJpaEntity);

        return certificationToken;
    }
}
