package moim_today.domain.certification_token;

import lombok.Builder;
import moim_today.global.error.BadRequestException;
import moim_today.persistence.entity.certification_token.CertificationTokenJpaEntity;

import java.time.LocalDateTime;
import java.util.UUID;

import static moim_today.global.constant.exception.CertificationConstant.CERTIFICATION_EXPIRED_ERROR;

@Builder
public record CertificationToken(
        String email,
        String certificationToken,
        CertificationType certificationType,
        LocalDateTime expiredDateTime
) {

    public static CertificationToken toDomain(final CertificationTokenJpaEntity certificationTokenJpaEntity) {
        return CertificationToken.builder()
                .email(certificationTokenJpaEntity.getEmail())
                .certificationToken(certificationTokenJpaEntity.getCertificationToken())
                .certificationType(certificationTokenJpaEntity.getCertificationType())
                .expiredDateTime(certificationTokenJpaEntity.getExpiredDateTime())
                .build();
    }

    public static String createCertificationToken() {
        UUID uuid = UUID.randomUUID();
        String token = uuid.toString().replace("-", "");
        return token.substring(0, 16);
    }

    public void validateExpiredDateTime(final LocalDateTime now) {
        if (expiredDateTime.isAfter(now)) {
            throw new BadRequestException(CERTIFICATION_EXPIRED_ERROR.message());
        }
    }
}