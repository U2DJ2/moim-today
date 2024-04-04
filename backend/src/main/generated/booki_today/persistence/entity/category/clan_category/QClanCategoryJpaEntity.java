package booki_today.persistence.entity.category.clan_category;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QClanCategoryJpaEntity is a Querydsl query type for ClanCategoryJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClanCategoryJpaEntity extends EntityPathBase<ClanCategoryJpaEntity> {

    private static final long serialVersionUID = 554442467L;

    public static final QClanCategoryJpaEntity clanCategoryJpaEntity = new QClanCategoryJpaEntity("clanCategoryJpaEntity");

    public final booki_today.persistence.entity.QBaseTimeEntity _super = new booki_today.persistence.entity.QBaseTimeEntity(this);

    public final NumberPath<Long> categoryId = createNumber("categoryId", Long.class);

    public final NumberPath<Long> clanId = createNumber("clanId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public QClanCategoryJpaEntity(String variable) {
        super(ClanCategoryJpaEntity.class, forVariable(variable));
    }

    public QClanCategoryJpaEntity(Path<? extends ClanCategoryJpaEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClanCategoryJpaEntity(PathMetadata metadata) {
        super(ClanCategoryJpaEntity.class, metadata);
    }

}

