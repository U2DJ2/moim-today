package booki_today.persistence.entity.custom_filter.custom_filter_status;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCustomFilterStatusJpaEntity is a Querydsl query type for CustomFilterStatusJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustomFilterStatusJpaEntity extends EntityPathBase<CustomFilterStatusJpaEntity> {

    private static final long serialVersionUID = -855364808L;

    public static final QCustomFilterStatusJpaEntity customFilterStatusJpaEntity = new QCustomFilterStatusJpaEntity("customFilterStatusJpaEntity");

    public final booki_today.persistence.entity.QBaseTimeEntity _super = new booki_today.persistence.entity.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> customFilterId = createNumber("customFilterId", Long.class);

    public final BooleanPath filterStatus = createBoolean("filterStatus");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public QCustomFilterStatusJpaEntity(String variable) {
        super(CustomFilterStatusJpaEntity.class, forVariable(variable));
    }

    public QCustomFilterStatusJpaEntity(Path<? extends CustomFilterStatusJpaEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCustomFilterStatusJpaEntity(PathMetadata metadata) {
        super(CustomFilterStatusJpaEntity.class, metadata);
    }

}

