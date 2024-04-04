package booki_today.persistence.entity.assemble.assemble;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAssembleJpaEntity is a Querydsl query type for AssembleJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAssembleJpaEntity extends EntityPathBase<AssembleJpaEntity> {

    private static final long serialVersionUID = -304013572L;

    public static final QAssembleJpaEntity assembleJpaEntity = new QAssembleJpaEntity("assembleJpaEntity");

    public final booki_today.persistence.entity.QBaseTimeEntity _super = new booki_today.persistence.entity.QBaseTimeEntity(this);

    public final DateTimePath<java.time.LocalDateTime> assembleEndDateTime = createDateTime("assembleEndDateTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> assembleStartDateTime = createDateTime("assembleStartDateTime", java.time.LocalDateTime.class);

    public final EnumPath<booki_today.domain.assemble.AssembleType> assembleType = createEnum("assembleType", booki_today.domain.assemble.AssembleType.class);

    public final NumberPath<Integer> attendanceNumber = createNumber("attendanceNumber", Integer.class);

    public final NumberPath<Integer> capacity = createNumber("capacity", Integer.class);

    public final NumberPath<Long> categoryId = createNumber("categoryId", Long.class);

    public final StringPath contents = createString("contents");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Integer> currentCount = createNumber("currentCount", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final StringPath password = createString("password");

    public final StringPath place = createString("place");

    public final DateTimePath<java.time.LocalDateTime> recruitmentEndDateTime = createDateTime("recruitmentEndDateTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> recruitmentStartDateTime = createDateTime("recruitmentStartDateTime", java.time.LocalDateTime.class);

    public final StringPath title = createString("title");

    public final NumberPath<Long> views = createNumber("views", Long.class);

    public QAssembleJpaEntity(String variable) {
        super(AssembleJpaEntity.class, forVariable(variable));
    }

    public QAssembleJpaEntity(Path<? extends AssembleJpaEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAssembleJpaEntity(PathMetadata metadata) {
        super(AssembleJpaEntity.class, metadata);
    }

}

