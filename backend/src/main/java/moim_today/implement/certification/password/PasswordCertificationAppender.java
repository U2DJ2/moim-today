package moim_today.implement.certification.password;

import moim_today.domain.certification_token.CertificationToken;
import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.certification.password.PasswordCertificationJpaEntity;
import moim_today.persistence.repository.certification.password.PasswordCertificationRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static java.time.LocalDateTime.*;
import static moim_today.global.constant.TimeConstant.*;

@Implement
public class PasswordCertificationAppender {

    private final PasswordCertificationRepository passwordCertificationRepository;

    public PasswordCertificationAppender(final PasswordCertificationRepository passwordCertificationRepository) {
        this.passwordCertificationRepository = passwordCertificationRepository;
    }

    @Transactional
    public String createPasswordToken(final String email) {
        Optional<PasswordCertificationJpaEntity> optionalEntity = passwordCertificationRepository.findByEmail(email);
        String passwordToken = CertificationToken.createCertificationToken();

        if (optionalEntity.isPresent()) {
            updatePasswordToken(optionalEntity.get(), passwordToken);
        } else {
            createPasswordToken(email, passwordToken);
        }

        return passwordToken;
    }

    private void updatePasswordToken(final PasswordCertificationJpaEntity passwordCertificationJpaEntity,
                                     final String passwordToken) {
        passwordCertificationJpaEntity.updateToken(
                passwordToken, now().plusMinutes(TEN_MINUTES.time())
        );
    }

    private void createPasswordToken(final String email, final String randomToken) {
        PasswordCertificationJpaEntity passwordCertificationJpaEntity =
                PasswordCertificationJpaEntity.toEntity(
                        email, randomToken, now().plusMinutes(TEN_MINUTES.time())
                );

        passwordCertificationRepository.save(passwordCertificationJpaEntity);
    }
}