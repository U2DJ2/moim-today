package moim_today.persistence.entity.certification.email;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import moim_today.global.base_entity.BaseTimeEntity;

import java.time.LocalDateTime;

@Getter
@Table(name = "email_certification")
@Entity
public class EmailCertificationJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_certification_id")
    private long id;

    private String email;

    private boolean certificationStatus;

    private LocalDateTime expiredDateTime;

    protected EmailCertificationJpaEntity() {
    }

    @Builder
    private EmailCertificationJpaEntity(final String email, final boolean certificationStatus,
                                       final LocalDateTime expiredDateTime) {
        this.email = email;
        this.certificationStatus = certificationStatus;
        this.expiredDateTime = expiredDateTime;
    }

    public static EmailCertificationJpaEntity toEntity(final String email, final LocalDateTime expiredDateTime) {
        return EmailCertificationJpaEntity.builder()
                .email(email)
                .certificationStatus(false)
                .expiredDateTime(expiredDateTime)
                .build();
    }
}
