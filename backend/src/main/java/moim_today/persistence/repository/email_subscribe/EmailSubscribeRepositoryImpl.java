package moim_today.persistence.repository.email_subscribe;

import com.querydsl.jpa.impl.JPAQueryFactory;
import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.email_subscribe.EmailSubscribeJpaEntity;
import org.springframework.stereotype.Repository;

import static moim_today.global.constant.exception.MailExceptionConstant.MAIL_SUBSCRIBE_NOT_FOUND_ERROR;
import static moim_today.persistence.entity.email_subscribe.QEmailSubscribeJpaEntity.*;

@Repository
public class EmailSubscribeRepositoryImpl implements EmailSubscribeRepository {

    private final EmailSubscribeJpaRepository emailSubscribeJpaRepository;
    private final JPAQueryFactory queryFactory;

    public EmailSubscribeRepositoryImpl(final EmailSubscribeJpaRepository emailSubscribeJpaRepository,
                                        final JPAQueryFactory queryFactory) {
        this.emailSubscribeJpaRepository = emailSubscribeJpaRepository;
        this.queryFactory = queryFactory;
    }

    @Override
    public EmailSubscribeJpaEntity getById(final long emailSubscribeId) {
        return emailSubscribeJpaRepository.findById(emailSubscribeId)
                .orElseThrow(() -> new NotFoundException(MAIL_SUBSCRIBE_NOT_FOUND_ERROR.message()));
    }

    @Override
    public EmailSubscribeJpaEntity getByMemberId(final long memberId) {
        return emailSubscribeJpaRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NotFoundException(MAIL_SUBSCRIBE_NOT_FOUND_ERROR.message()));
    }

    @Override
    public EmailSubscribeJpaEntity save(final EmailSubscribeJpaEntity emailSubscribeJpaEntity) {
        return emailSubscribeJpaRepository.save(emailSubscribeJpaEntity);
    }

    @Override
    public boolean getSubscriptionStatus(final long memberId) {
        return queryFactory.select(emailSubscribeJpaEntity.subscribeStatus)
                .from(emailSubscribeJpaEntity)
                .where(emailSubscribeJpaEntity.memberId.eq(memberId))
                .fetchFirst();
    }
}
