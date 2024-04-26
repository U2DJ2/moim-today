package moim_today.implement.certification.email;

import moim_today.domain.certification.Certification;
import moim_today.global.annotation.Implement;
import moim_today.implement.university.UniversityFinder;
import moim_today.persistence.entity.certification.email.EmailCertificationJpaEntity;
import moim_today.persistence.repository.certification.email.EmailCertificationRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;


@Implement
public class EmailCertificationAppender {

    private final EmailCertificationRepository emailCertificationRepository;
    private final UniversityFinder universityFinder;

    public EmailCertificationAppender(final EmailCertificationRepository emailCertificationRepository,
                                      final UniversityFinder universityFinder) {
        this.emailCertificationRepository = emailCertificationRepository;
        this.universityFinder = universityFinder;
    }

    @Transactional
    public String createEmailToken(final String email, final LocalDateTime expiredDateTime) {
        Optional<EmailCertificationJpaEntity> optionalEntity = emailCertificationRepository.findByEmail(email);
        String emailToken = Certification.createCertificationToken();

        if (optionalEntity.isPresent()) {
            updateEmailToken(optionalEntity.get(), emailToken, expiredDateTime);
        } else {
            saveNewEmailToken(email, emailToken, expiredDateTime);
        }

        return emailToken;
    }

    private void updateEmailToken(final EmailCertificationJpaEntity emailCertificationJpaEntity,
                                  final String passwordToken, final LocalDateTime expiredDateTime) {
        emailCertificationJpaEntity.updateToken(passwordToken, expiredDateTime);
    }

    private void saveNewEmailToken(final String email, final String emailToken, final LocalDateTime expiredDateTime) {
        EmailCertificationJpaEntity emailCertificationJpaEntity =
                EmailCertificationJpaEntity.toEntity(email, emailToken, expiredDateTime);

        Certification certification = Certification.toDomain(emailCertificationJpaEntity);
        String emailDomain = certification.parseEmailDomain();
        universityFinder.validateExists(emailDomain);

        emailCertificationRepository.save(emailCertificationJpaEntity);
    }
}
