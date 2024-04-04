package booki_today.persistence.entity.category.category;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCategoryJpaEntity is a Querydsl query type for CategoryJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCategoryJpaEntity extends EntityPathBase<CategoryJpaEntity> {

    private static final long serialVersionUID = 1874322128L;

    public static final QCategoryJpaEntity categoryJpaEntity = new QCategoryJpaEntity("categoryJpaEntity");

    public final booki_today.persistence.entity.QBaseTimeEntity _super = new booki_today.persistence.entity.QBaseTimeEntity(this);

    public final EnumPath<booki_today.domain.category.CategoryType> categoryType = createEnum("categoryType", booki_today.domain.category.CategoryType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public QCategoryJpaEntity(String variable) {
        super(CategoryJpaEntity.class, forVariable(variable));
    }

    public QCategoryJpaEntity(Path<? extends CategoryJpaEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCategoryJpaEntity(PathMetadata metadata) {
        super(CategoryJpaEntity.class, metadata);
    }

}

