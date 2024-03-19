package booki_today.persistence.entity.participated_booki;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QParticipatedBookiJpaEntity is a Querydsl query type for ParticipatedBookiJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QParticipatedBookiJpaEntity extends EntityPathBase<ParticipatedBookiJpaEntity> {

    private static final long serialVersionUID = -585991045L;

    public static final QParticipatedBookiJpaEntity participatedBookiJpaEntity = new QParticipatedBookiJpaEntity("participatedBookiJpaEntity");

    public final booki_today.persistence.entity.QBaseTimeEntity _super = new booki_today.persistence.entity.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> createdBookiId = createNumber("createdBookiId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final EnumPath<booki_today.domain.participated_booki.ParticipatedStatus> participatedStatus = createEnum("participatedStatus", booki_today.domain.participated_booki.ParticipatedStatus.class);

    public QParticipatedBookiJpaEntity(String variable) {
        super(ParticipatedBookiJpaEntity.class, forVariable(variable));
    }

    public QParticipatedBookiJpaEntity(Path<? extends ParticipatedBookiJpaEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QParticipatedBookiJpaEntity(PathMetadata metadata) {
        super(ParticipatedBookiJpaEntity.class, metadata);
    }

}

