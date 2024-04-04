package booki_today.persistence.entity.custom_filter.custom_filter;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCustomFilterJpaEntity is a Querydsl query type for CustomFilterJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustomFilterJpaEntity extends EntityPathBase<CustomFilterJpaEntity> {

    private static final long serialVersionUID = 1156411925L;

    public static final QCustomFilterJpaEntity customFilterJpaEntity = new QCustomFilterJpaEntity("customFilterJpaEntity");

    public final booki_today.persistence.entity.QBaseTimeEntity _super = new booki_today.persistence.entity.QBaseTimeEntity(this);

    public final NumberPath<Long> assembleId = createNumber("assembleId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath customFilterName = createString("customFilterName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public QCustomFilterJpaEntity(String variable) {
        super(CustomFilterJpaEntity.class, forVariable(variable));
    }

    public QCustomFilterJpaEntity(Path<? extends CustomFilterJpaEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCustomFilterJpaEntity(PathMetadata metadata) {
        super(CustomFilterJpaEntity.class, metadata);
    }

}

