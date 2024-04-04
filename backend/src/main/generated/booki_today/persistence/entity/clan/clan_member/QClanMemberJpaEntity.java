package booki_today.persistence.entity.clan.clan_member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QClanMemberJpaEntity is a Querydsl query type for ClanMemberJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClanMemberJpaEntity extends EntityPathBase<ClanMemberJpaEntity> {

    private static final long serialVersionUID = 1131616443L;

    public static final QClanMemberJpaEntity clanMemberJpaEntity = new QClanMemberJpaEntity("clanMemberJpaEntity");

    public final booki_today.persistence.entity.QBaseTimeEntity _super = new booki_today.persistence.entity.QBaseTimeEntity(this);

    public final NumberPath<Long> clanId = createNumber("clanId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public QClanMemberJpaEntity(String variable) {
        super(ClanMemberJpaEntity.class, forVariable(variable));
    }

    public QClanMemberJpaEntity(Path<? extends ClanMemberJpaEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClanMemberJpaEntity(PathMetadata metadata) {
        super(ClanMemberJpaEntity.class, metadata);
    }

}

