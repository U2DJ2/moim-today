package booki_today.persistence.entity.category.university_category;

import booki_today.global.annotation.Association;
import booki_today.persistence.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Table(name = "university_category")
@Entity
public class UniversityCategoryJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "university_category_id")
    private long id;

    @Association
    private long categoryId;

    @Association
    private long universityId;

    protected UniversityCategoryJpaEntity() {
    }

    @Builder
    private UniversityCategoryJpaEntity(final long categoryId, final long universityId) {
        this.categoryId = categoryId;
        this.universityId = universityId;
    }
}
