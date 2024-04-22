package moim_today.implement.certification.password;

import moim_today.domain.certification.Certification;
import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.certification.password.PasswordCertificationJpaEntity;
import moim_today.persistence.repository.certification.password.PasswordCertificationRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;


@Implement
public class PasswordCertificationAppender {

    private final PasswordCertificationRepository passwordCertificationRepository;

    public PasswordCertificationAppender(final PasswordCertificationRepository passwordCertificationRepository) {
        this.passwordCertificationRepository = passwordCertificationRepository;
    }

    @Transactional
    public String createPasswordToken(final String email, final LocalDateTime expiredDateTime) {
        Optional<PasswordCertificationJpaEntity> optionalEntity = passwordCertificationRepository.findByEmail(email);
        String passwordToken = Certification.createCertificationToken();

        if (optionalEntity.isPresent()) {
            updatePasswordToken(optionalEntity.get(), passwordToken, expiredDateTime);
        } else {
            saveNewPasswordToken(email, passwordToken, expiredDateTime);
        }

        return passwordToken;
    }

    private void updatePasswordToken(final PasswordCertificationJpaEntity passwordCertificationJpaEntity,
                                     final String passwordToken, final LocalDateTime expiredDateTime) {
        passwordCertificationJpaEntity.updateToken(passwordToken, expiredDateTime);
    }

    private void saveNewPasswordToken(final String email, final String passwordToken,
                                      final LocalDateTime expiredDateTime) {
        PasswordCertificationJpaEntity passwordCertificationJpaEntity =
                PasswordCertificationJpaEntity.toEntity(email, passwordToken, expiredDateTime);

        passwordCertificationRepository.save(passwordCertificationJpaEntity);
    }
}