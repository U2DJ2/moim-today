package booki_today.persistence.entity.category.university_category;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUniversityCategoryJpaEntity is a Querydsl query type for UniversityCategoryJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUniversityCategoryJpaEntity extends EntityPathBase<UniversityCategoryJpaEntity> {

    private static final long serialVersionUID = -232285773L;

    public static final QUniversityCategoryJpaEntity universityCategoryJpaEntity = new QUniversityCategoryJpaEntity("universityCategoryJpaEntity");

    public final booki_today.persistence.entity.QBaseTimeEntity _super = new booki_today.persistence.entity.QBaseTimeEntity(this);

    public final NumberPath<Long> categoryId = createNumber("categoryId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public final NumberPath<Long> universityId = createNumber("universityId", Long.class);

    public QUniversityCategoryJpaEntity(String variable) {
        super(UniversityCategoryJpaEntity.class, forVariable(variable));
    }

    public QUniversityCategoryJpaEntity(Path<? extends UniversityCategoryJpaEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUniversityCategoryJpaEntity(PathMetadata metadata) {
        super(UniversityCategoryJpaEntity.class, metadata);
    }

}

