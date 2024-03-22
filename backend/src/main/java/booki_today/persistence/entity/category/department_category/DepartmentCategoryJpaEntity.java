package booki_today.persistence.entity.category.department_category;

import booki_today.global.annotation.Association;
import booki_today.persistence.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Table(name = "department_category")
@Entity
public class DepartmentCategoryJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_category_id")
    private long id;

    @Association
    private long categoryId;

    @Association
    private long departmentId;

    protected DepartmentCategoryJpaEntity() {
    }

    @Builder
    private DepartmentCategoryJpaEntity(final long categoryId, final long departmentId) {
        this.categoryId = categoryId;
        this.departmentId = departmentId;
    }
}
