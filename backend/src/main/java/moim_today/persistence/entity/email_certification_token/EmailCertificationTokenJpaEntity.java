package moim_today.persistence.entity.email_certification_token;

import moim_today.global.base_entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Table(name = "email_certification_token")
@Entity
public class EmailCertificationTokenJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_certification_token_id")
    private long id;

    private String email;

    private String certificationToken;

    private LocalDateTime expiredDateTime;

    protected EmailCertificationTokenJpaEntity() {
    }

    @Builder
    public EmailCertificationTokenJpaEntity(final String email, final String certificationToken,
                                            final LocalDateTime expiredDateTime) {
        this.email = email;
        this.certificationToken = certificationToken;
        this.expiredDateTime = expiredDateTime;
    }
}
