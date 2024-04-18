package moim_today.implement.certification.email;

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
    public void save(final String email, final LocalDateTime expiredDateTime) {
        EmailCertificationJpaEntity emailCertificationJpaEntity
                = EmailCertificationJpaEntity.toEntity(email, expiredDateTime);
        emailCertificationRepository.save(emailCertificationJpaEntity);
    }
}
