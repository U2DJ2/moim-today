package booki_today.persistence.entity.category.category;

import booki_today.domain.category.CategoryType;
import booki_today.persistence.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Table(name = "category")
@Entity
public class CategoryJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private long id;

    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;

    protected CategoryJpaEntity() {
    }

    @Builder
    private CategoryJpaEntity(final CategoryType categoryType) {
        this.categoryType = categoryType;
    }
}
