package moim_today.domain.certification_token;

import lombok.Builder;
import moim_today.global.error.BadRequestException;
import moim_today.persistence.entity.certification.password.PasswordCertificationJpaEntity;

import java.time.LocalDateTime;
import java.util.UUID;

import static moim_today.global.constant.NumberConstant.*;
import static moim_today.global.constant.SymbolConstant.*;
import static moim_today.global.constant.exception.CertificationConstant.CERTIFICATION_EXPIRED_ERROR;

@Builder
public record CertificationToken(
        String email,
        String certificationToken,
        LocalDateTime expiredDateTime
) {

    public static CertificationToken toDomain(final PasswordCertificationJpaEntity passwordCertificationJpaEntity) {
        return CertificationToken.builder()
                .email(passwordCertificationJpaEntity.getEmail())
                .certificationToken(passwordCertificationJpaEntity.getCertificationToken())
                .expiredDateTime(passwordCertificationJpaEntity.getExpiredDateTime())
                .build();
    }

    public static String createCertificationToken() {
        UUID uuid = UUID.randomUUID();
        String token = uuid.toString().replace(HYPHEN.value(), BLANK.value());
        return token.substring(CERTIFICATION_TOKEN_START_POINT.value(), CERTIFICATION_TOKEN_LENGTH.value());
    }

    public void validateExpiredDateTime(final LocalDateTime now) {
        if (expiredDateTime.isBefore(now)) {
            throw new BadRequestException(CERTIFICATION_EXPIRED_ERROR.message());
        }
    }
}