package booki_today.persistence.entity.assemble.participated_assemble;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QParticipatedAssembleJpaEntity is a Querydsl query type for ParticipatedAssembleJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QParticipatedAssembleJpaEntity extends EntityPathBase<ParticipatedAssembleJpaEntity> {

    private static final long serialVersionUID = -886680489L;

    public static final QParticipatedAssembleJpaEntity participatedAssembleJpaEntity = new QParticipatedAssembleJpaEntity("participatedAssembleJpaEntity");

    public final booki_today.persistence.entity.QBaseTimeEntity _super = new booki_today.persistence.entity.QBaseTimeEntity(this);

    public final NumberPath<Long> assembleId = createNumber("assembleId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final EnumPath<booki_today.domain.participated_assemble.ParticipatedStatus> participatedStatus = createEnum("participatedStatus", booki_today.domain.participated_assemble.ParticipatedStatus.class);

    public QParticipatedAssembleJpaEntity(String variable) {
        super(ParticipatedAssembleJpaEntity.class, forVariable(variable));
    }

    public QParticipatedAssembleJpaEntity(Path<? extends ParticipatedAssembleJpaEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QParticipatedAssembleJpaEntity(PathMetadata metadata) {
        super(ParticipatedAssembleJpaEntity.class, metadata);
    }

}

