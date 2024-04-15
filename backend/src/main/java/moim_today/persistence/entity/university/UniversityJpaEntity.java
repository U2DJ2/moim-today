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

    protected UniversityJpaEntity() {
    }

    @Builder
    public UniversityJpaEntity(final String universityName, final String universityEmail) {
        this.universityName = universityName;
        this.universityEmail = universityEmail;
    }

    public void updateEmail(String universityEmail){
        this.universityEmail = universityEmail;
    }
}
