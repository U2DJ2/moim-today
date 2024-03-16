package booki_today.persistence.entity.email_certification_number;

import booki_today.persistence.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Table(name = "email_certification_number")
@Entity
public class EmailCertificationNumberJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_certification_number_id")
    private Long id;

    private String email;

    private String certificationNumber;

    protected EmailCertificationNumberJpaEntity() {
    }

    @Builder
    private EmailCertificationNumberJpaEntity(final String email, final String certificationNumber) {
        this.email = email;
        this.certificationNumber = certificationNumber;
    }
}
