package booki_today.persistence.entity.email_certification_number;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEmailCertificationNumberJpaEntity is a Querydsl query type for EmailCertificationNumberJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEmailCertificationNumberJpaEntity extends EntityPathBase<EmailCertificationNumberJpaEntity> {

    private static final long serialVersionUID = -459464278L;

    public static final QEmailCertificationNumberJpaEntity emailCertificationNumberJpaEntity = new QEmailCertificationNumberJpaEntity("emailCertificationNumberJpaEntity");

    public final booki_today.persistence.entity.QBaseTimeEntity _super = new booki_today.persistence.entity.QBaseTimeEntity(this);

    public final StringPath certificationNumber = createString("certificationNumber");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public QEmailCertificationNumberJpaEntity(String variable) {
        super(EmailCertificationNumberJpaEntity.class, forVariable(variable));
    }

    public QEmailCertificationNumberJpaEntity(Path<? extends EmailCertificationNumberJpaEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEmailCertificationNumberJpaEntity(PathMetadata metadata) {
        super(EmailCertificationNumberJpaEntity.class, metadata);
    }

}

