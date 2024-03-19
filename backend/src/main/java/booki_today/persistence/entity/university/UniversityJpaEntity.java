package booki_today.persistence.entity.university;

import booki_today.persistence.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Table(name = "university")
@Entity
public class UniversityJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "university_id")
    private Long id;

    private String universityName;

    private String universityDescription;

    private String universityImageUrl;

    protected UniversityJpaEntity() {
    }

    @Builder
    private UniversityJpaEntity(final String universityName, final String universityDescription,
                                final String universityImageUrl) {
        this.universityName = universityName;
        this.universityDescription = universityDescription;
        this.universityImageUrl = universityImageUrl;
    }
}
