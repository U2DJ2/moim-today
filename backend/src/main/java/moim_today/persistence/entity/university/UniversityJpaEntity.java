package moim_today.persistence.entity.university;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import moim_today.global.base_entity.BaseTimeEntity;

@Getter
@Table(name = "university")
@Entity
public class UniversityJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "university_id")
    private long id;

    private String universityName;

    private String universityEmail;

    private String adminPassword;

    protected UniversityJpaEntity() {
    }

    @Builder
    public UniversityJpaEntity(final long id,
                               final String universityName,
                               final String universityEmail,
                               final String adminPassword) {
        this.id = id;
        this.universityName = universityName;
        this.universityEmail = universityEmail;
        this.adminPassword = adminPassword;
    }

    public void updateEmail(final String universityEmail){
        this.universityEmail = universityEmail;
    }

    public void updateAdminPassword(final String adminPassword) {
        this.adminPassword = adminPassword;
    }
}
