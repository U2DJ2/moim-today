package booki_today.persistence.entity.clan.clan_member_candidate;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QClanMemberCandidateJpaEntity is a Querydsl query type for ClanMemberCandidateJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClanMemberCandidateJpaEntity extends EntityPathBase<ClanMemberCandidateJpaEntity> {

    private static final long serialVersionUID = -2041816806L;

    public static final QClanMemberCandidateJpaEntity clanMemberCandidateJpaEntity = new QClanMemberCandidateJpaEntity("clanMemberCandidateJpaEntity");

    public final booki_today.persistence.entity.QBaseTimeEntity _super = new booki_today.persistence.entity.QBaseTimeEntity(this);

    public final NumberPath<Long> clanId = createNumber("clanId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public QClanMemberCandidateJpaEntity(String variable) {
        super(ClanMemberCandidateJpaEntity.class, forVariable(variable));
    }

    public QClanMemberCandidateJpaEntity(Path<? extends ClanMemberCandidateJpaEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClanMemberCandidateJpaEntity(PathMetadata metadata) {
        super(ClanMemberCandidateJpaEntity.class, metadata);
    }

}

