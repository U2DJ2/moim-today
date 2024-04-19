package moim_today.implement.certification.email;

import moim_today.domain.certification.Certification;
import moim_today.persistence.entity.certification.email.EmailCertificationJpaEntity;
import moim_today.persistence.repository.certification.email.EmailCertificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.time.LocalDateTime.now;
import static moim_today.global.constant.TimeConstant.TEN_MINUTES;

@Service
public class EmailCertificationAppender {

    private final EmailCertificationRepository emailCertificationRepository;

    public EmailCertificationAppender(final EmailCertificationRepository emailCertificationRepository) {
        this.emailCertificationRepository = emailCertificationRepository;
    }

    @Transactional
    public String createEmailToken(final String email, final LocalDateTime expiredDateTime) {
        Optional<EmailCertificationJpaEntity> optionalEntity = emailCertificationRepository.findByEmail(email);
        String emailToken = Certification.createCertificationToken();

        if (optionalEntity.isPresent()) {
            updateEmailToken(optionalEntity.get(), emailToken);
        } else {
            saveNewEmailToken(email, emailToken);
        }

        EmailCertificationJpaEntity emailCertificationJpaEntity
                = EmailCertificationJpaEntity.toEntity(email, emailToken, expiredDateTime);
        emailCertificationRepository.save(emailCertificationJpaEntity);

        return emailToken;
    }

    private void updateEmailToken(final EmailCertificationJpaEntity emailCertificationJpaEntity,
                                  final String passwordToken) {
        emailCertificationJpaEntity.updateToken(
                passwordToken, now().plusMinutes(TEN_MINUTES.time())
        );
    }

    private void saveNewEmailToken(final String email, final String emailToken) {
        EmailCertificationJpaEntity emailCertificationJpaEntity =
                EmailCertificationJpaEntity.toEntity(
                        email, emailToken, now().plusMinutes(TEN_MINUTES.time())
                );

        emailCertificationRepository.save(emailCertificationJpaEntity);
    }
}
