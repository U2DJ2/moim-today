package booki_today.persistence.entity.created_booki;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCreatedBookiJpaEntity is a Querydsl query type for CreatedBookiJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCreatedBookiJpaEntity extends EntityPathBase<CreatedBookiJpaEntity> {

    private static final long serialVersionUID = 547268817L;

    public static final QCreatedBookiJpaEntity createdBookiJpaEntity = new QCreatedBookiJpaEntity("createdBookiJpaEntity");

    public final booki_today.persistence.entity.QBaseTimeEntity _super = new booki_today.persistence.entity.QBaseTimeEntity(this);

    public final NumberPath<Integer> attendanceNumber = createNumber("attendanceNumber", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> bookiEndDateTime = createDateTime("bookiEndDateTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> bookiStartDateTime = createDateTime("bookiStartDateTime", java.time.LocalDateTime.class);

    public final EnumPath<booki_today.domain.created_booki.BookiType> bookiType = createEnum("bookiType", booki_today.domain.created_booki.BookiType.class);

    public final NumberPath<Integer> capacity = createNumber("capacity", Integer.class);

    public final NumberPath<Long> categoryId = createNumber("categoryId", Long.class);

    public final StringPath contents = createString("contents");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Integer> currentCount = createNumber("currentCount", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> eventEndDateTime = createDateTime("eventEndDateTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> eventStartDateTime = createDateTime("eventStartDateTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> expiredDateTime = createDateTime("expiredDateTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final StringPath password = createString("password");

    public final StringPath place = createString("place");

    public final StringPath title = createString("title");

    public final NumberPath<Long> views = createNumber("views", Long.class);

    public QCreatedBookiJpaEntity(String variable) {
        super(CreatedBookiJpaEntity.class, forVariable(variable));
    }

    public QCreatedBookiJpaEntity(Path<? extends CreatedBookiJpaEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCreatedBookiJpaEntity(PathMetadata metadata) {
        super(CreatedBookiJpaEntity.class, metadata);
    }

}

