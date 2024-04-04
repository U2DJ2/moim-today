package booki_today.persistence.entity.department;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDepartmentJpaEntity is a Querydsl query type for DepartmentJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDepartmentJpaEntity extends EntityPathBase<DepartmentJpaEntity> {

    private static final long serialVersionUID = -706370304L;

    public static final QDepartmentJpaEntity departmentJpaEntity = new QDepartmentJpaEntity("departmentJpaEntity");

    public final booki_today.persistence.entity.QBaseTimeEntity _super = new booki_today.persistence.entity.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath departmentName = createString("departmentName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public final NumberPath<Long> universityId = createNumber("universityId", Long.class);

    public QDepartmentJpaEntity(String variable) {
        super(DepartmentJpaEntity.class, forVariable(variable));
    }

    public QDepartmentJpaEntity(Path<? extends DepartmentJpaEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDepartmentJpaEntity(PathMetadata metadata) {
        super(DepartmentJpaEntity.class, metadata);
    }

}

