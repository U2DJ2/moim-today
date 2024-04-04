package booki_today.persistence.entity.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMemberJpaEntity is a Querydsl query type for MemberJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberJpaEntity extends EntityPathBase<MemberJpaEntity> {

    private static final long serialVersionUID = -958113792L;

    public static final QMemberJpaEntity memberJpaEntity = new QMemberJpaEntity("memberJpaEntity");

    public final booki_today.persistence.entity.QBaseTimeEntity _super = new booki_today.persistence.entity.QBaseTimeEntity(this);

    public final DatePath<java.time.LocalDate> birthDate = createDate("birthDate", java.time.LocalDate.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> departmentId = createNumber("departmentId", Long.class);

    public final StringPath email = createString("email");

    public final EnumPath<booki_today.domain.member.Gender> gender = createEnum("gender", booki_today.domain.member.Gender.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public final StringPath memberProfileImageUrl = createString("memberProfileImageUrl");

    public final StringPath password = createString("password");

    public final StringPath studentId = createString("studentId");

    public final NumberPath<Long> universityId = createNumber("universityId", Long.class);

    public final StringPath username = createString("username");

    public QMemberJpaEntity(String variable) {
        super(MemberJpaEntity.class, forVariable(variable));
    }

    public QMemberJpaEntity(Path<? extends MemberJpaEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemberJpaEntity(PathMetadata metadata) {
        super(MemberJpaEntity.class, metadata);
    }

}

