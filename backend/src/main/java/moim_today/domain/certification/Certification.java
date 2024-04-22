package moim_today.domain.certification;

import lombok.Builder;
import moim_today.global.error.BadRequestException;
import moim_today.global.error.HandleExceptionPage;
import moim_today.persistence.entity.certification.email.EmailCertificationJpaEntity;
import moim_today.persistence.entity.certification.password.PasswordCertificationJpaEntity;

import java.time.LocalDateTime;
import java.util.UUID;

import static moim_today.global.constant.NumberConstant.*;
import static moim_today.global.constant.SymbolConstant.*;
import static moim_today.global.constant.exception.CertificationConstant.CERTIFICATION_EXPIRED_ERROR;
import static moim_today.global.constant.exception.MailExceptionConstant.WRONG_EMAIL_FROM_ERROR;

@Builder
public record Certification(
        String email,
        String certificationToken,
        LocalDateTime expiredDateTime
) {

    public static Certification toDomain(final PasswordCertificationJpaEntity passwordCertificationJpaEntity) {
        return Certification.builder()
                .email(passwordCertificationJpaEntity.getEmail())
                .certificationToken(passwordCertificationJpaEntity.getCertificationToken())
                .expiredDateTime(passwordCertificationJpaEntity.getExpiredDateTime())
                .build();
    }

    public static Certification toDomain(final EmailCertificationJpaEntity emailCertificationJpaEntity) {
        return Certification.builder()
                .email(emailCertificationJpaEntity.getEmail())
                .certificationToken(emailCertificationJpaEntity.getCertificationToken())
                .expiredDateTime(emailCertificationJpaEntity.getExpiredDateTime())
                .build();
    }

    public static String createCertificationToken() {
        UUID uuid = UUID.randomUUID();
        String token = uuid.toString().replace(HYPHEN.value(), BLANK.value());
        return token.substring(CERTIFICATION_TOKEN_START_POINT.value(), CERTIFICATION_TOKEN_LENGTH.value());
    }

    public void validateExpiredDateTime(final LocalDateTime now, final String exceptionPage) {
        if (expiredDateTime.isBefore(now)) {
            throw new HandleExceptionPage(exceptionPage);
        }
    }

    public String parseEmailDomain() {
        int atIndex = email.indexOf(AT.value());
        int lastIndex = email.length() - 1;
        int nextAtIndex = atIndex + 1;

        if (atIndex == NOT_EXIST_IDX.value() || atIndex == lastIndex) {
            throw new BadRequestException(WRONG_EMAIL_FROM_ERROR.message());
        }

        return email.substring(nextAtIndex);
    }
}