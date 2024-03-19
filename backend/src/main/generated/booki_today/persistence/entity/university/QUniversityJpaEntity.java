package booki_today.persistence.entity.university;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUniversityJpaEntity is a Querydsl query type for UniversityJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUniversityJpaEntity extends EntityPathBase<UniversityJpaEntity> {

    private static final long serialVersionUID = 1277694592L;

    public static final QUniversityJpaEntity universityJpaEntity = new QUniversityJpaEntity("universityJpaEntity");

    public final booki_today.persistence.entity.QBaseTimeEntity _super = new booki_today.persistence.entity.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public final StringPath universityDescription = createString("universityDescription");

    public final StringPath universityImageUrl = createString("universityImageUrl");

    public final StringPath universityName = createString("universityName");

    public QUniversityJpaEntity(String variable) {
        super(UniversityJpaEntity.class, forVariable(variable));
    }

    public QUniversityJpaEntity(Path<? extends UniversityJpaEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUniversityJpaEntity(PathMetadata metadata) {
        super(UniversityJpaEntity.class, metadata);
    }

}

