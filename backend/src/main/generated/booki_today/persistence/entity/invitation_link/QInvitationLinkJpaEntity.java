package booki_today.persistence.entity.invitation_link;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QInvitationLinkJpaEntity is a Querydsl query type for InvitationLinkJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInvitationLinkJpaEntity extends EntityPathBase<InvitationLinkJpaEntity> {

    private static final long serialVersionUID = 461653805L;

    public static final QInvitationLinkJpaEntity invitationLinkJpaEntity = new QInvitationLinkJpaEntity("invitationLinkJpaEntity");

    public final booki_today.persistence.entity.QBaseTimeEntity _super = new booki_today.persistence.entity.QBaseTimeEntity(this);

    public final NumberPath<Long> assembleId = createNumber("assembleId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath invitationLink = createString("invitationLink");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public final DateTimePath<java.time.LocalDateTime> linkExpiredDateTime = createDateTime("linkExpiredDateTime", java.time.LocalDateTime.class);

    public QInvitationLinkJpaEntity(String variable) {
        super(InvitationLinkJpaEntity.class, forVariable(variable));
    }

    public QInvitationLinkJpaEntity(Path<? extends InvitationLinkJpaEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QInvitationLinkJpaEntity(PathMetadata metadata) {
        super(InvitationLinkJpaEntity.class, metadata);
    }

}

