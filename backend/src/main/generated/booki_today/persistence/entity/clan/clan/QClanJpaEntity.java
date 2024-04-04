package booki_today.persistence.entity.clan.clan;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QClanJpaEntity is a Querydsl query type for ClanJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClanJpaEntity extends EntityPathBase<ClanJpaEntity> {

    private static final long serialVersionUID = -1956624520L;

    public static final QClanJpaEntity clanJpaEntity = new QClanJpaEntity("clanJpaEntity");

    public final booki_today.persistence.entity.QBaseTimeEntity _super = new booki_today.persistence.entity.QBaseTimeEntity(this);

    public final StringPath clanName = createString("clanName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public QClanJpaEntity(String variable) {
        super(ClanJpaEntity.class, forVariable(variable));
    }

    public QClanJpaEntity(Path<? extends ClanJpaEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClanJpaEntity(PathMetadata metadata) {
        super(ClanJpaEntity.class, metadata);
    }

}

