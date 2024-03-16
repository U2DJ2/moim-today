package booki_today.persistence.entity.category;

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
    private Long id;

    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;

    private String categoryName;

    protected CategoryJpaEntity() {
    }

    @Builder
    private CategoryJpaEntity(final CategoryType categoryType, final String categoryName) {
        this.categoryType = categoryType;
        this.categoryName = categoryName;
    }
}
