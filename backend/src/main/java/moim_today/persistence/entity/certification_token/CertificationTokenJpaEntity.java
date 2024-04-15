package moim_today.persistence.entity.certification_token;

import moim_today.domain.certification_token.CertificationType;
import moim_today.global.base_entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Table(name = "certification_token")
@Entity
public class CertificationTokenJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certification_token_id")
    private long id;

    private String email;

    private String certificationToken;

    @Enumerated(EnumType.STRING)
    private CertificationType certificationType;

    private LocalDateTime expiredDateTime;

    protected CertificationTokenJpaEntity() {
    }

    @Builder
    private CertificationTokenJpaEntity(final String email, final String certificationToken,
                                        final CertificationType certificationType, final LocalDateTime expiredDateTime) {
        this.email = email;
        this.certificationToken = certificationToken;
        this.certificationType = certificationType;
        this.expiredDateTime = expiredDateTime;
    }

    public static CertificationTokenJpaEntity toEntity(final String email, final String certificationToken,
                                                       final CertificationType certificationType,
                                                       final LocalDateTime expiredDateTime) {
        return CertificationTokenJpaEntity.builder()
                .email(email)
                .certificationToken(certificationToken)
                .certificationType(certificationType)
                .expiredDateTime(expiredDateTime)
                .build();
    }

    public void updateToken(final String certificationToken, final CertificationType certificationType,
                            final LocalDateTime expiredDateTime) {
        this.certificationToken = certificationToken;
        this.certificationType = certificationType;
        this.expiredDateTime = expiredDateTime;
    }
}
