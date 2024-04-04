package booki_today.persistence.entity.category.department_category;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDepartmentCategoryJpaEntity is a Querydsl query type for DepartmentCategoryJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDepartmentCategoryJpaEntity extends EntityPathBase<DepartmentCategoryJpaEntity> {

    private static final long serialVersionUID = -1679822037L;

    public static final QDepartmentCategoryJpaEntity departmentCategoryJpaEntity = new QDepartmentCategoryJpaEntity("departmentCategoryJpaEntity");

    public final booki_today.persistence.entity.QBaseTimeEntity _super = new booki_today.persistence.entity.QBaseTimeEntity(this);

    public final NumberPath<Long> categoryId = createNumber("categoryId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> departmentId = createNumber("departmentId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public QDepartmentCategoryJpaEntity(String variable) {
        super(DepartmentCategoryJpaEntity.class, forVariable(variable));
    }

    public QDepartmentCategoryJpaEntity(Path<? extends DepartmentCategoryJpaEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDepartmentCategoryJpaEntity(PathMetadata metadata) {
        super(DepartmentCategoryJpaEntity.class, metadata);
    }

}

