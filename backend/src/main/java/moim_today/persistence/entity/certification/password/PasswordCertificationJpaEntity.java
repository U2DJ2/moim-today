package moim_today.persistence.entity.certification.password;

import moim_today.global.base_entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Table(name = "password_certification")
@Entity
public class PasswordCertificationJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "password_certification_id")
    private long id;

    private String email;

    private String certificationToken;

    private LocalDateTime expiredDateTime;

    protected PasswordCertificationJpaEntity() {
    }

    @Builder
    private PasswordCertificationJpaEntity(final String email, final String certificationToken,
                                          final LocalDateTime expiredDateTime) {
        this.email = email;
        this.certificationToken = certificationToken;
        this.expiredDateTime = expiredDateTime;
    }

    public static PasswordCertificationJpaEntity toEntity(final String email, final String certificationToken,
                                                          final LocalDateTime expiredDateTime) {
        return PasswordCertificationJpaEntity.builder()
                .email(email)
                .certificationToken(certificationToken)
                .expiredDateTime(expiredDateTime)
                .build();
    }

    public void updateToken(final String certificationToken, final LocalDateTime expiredDateTime) {
        this.certificationToken = certificationToken;
        this.expiredDateTime = expiredDateTime;
    }
}
