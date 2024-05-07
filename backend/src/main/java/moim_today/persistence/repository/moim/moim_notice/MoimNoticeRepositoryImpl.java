package moim_today.persistence.repository.moim.moim_notice;

import com.querydsl.jpa.impl.JPAQueryFactory;
import moim_today.dto.moim.moim_notice.MoimNoticeSimpleResponse;
import moim_today.dto.moim.moim_notice.QMoimNoticeSimpleResponse;
import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.moim.moim_notice.MoimNoticeJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

import static moim_today.global.constant.exception.MoimExceptionConstant.NOTICE_NOT_FOUND_ERROR;
import static moim_today.persistence.entity.moim.moim_notice.QMoimNoticeJpaEntity.moimNoticeJpaEntity;

@Repository
public class MoimNoticeRepositoryImpl implements MoimNoticeRepository {

    private final MoimNoticeJpaRepository moimNoticeJpaRepository;
    private final JPAQueryFactory queryFactory;

    public MoimNoticeRepositoryImpl(final MoimNoticeJpaRepository moimNoticeJpaRepository,
                                    final JPAQueryFactory queryFactory) {
        this.moimNoticeJpaRepository = moimNoticeJpaRepository;
        this.queryFactory = queryFactory;
    }

    @Override
    public void save(final MoimNoticeJpaEntity moimNoticeJpaEntity) {
        moimNoticeJpaRepository.save(moimNoticeJpaEntity);
    }

    @Override
    public long count() {
        return moimNoticeJpaRepository.count();
    }

    @Override
    public List<MoimNoticeSimpleResponse> findAllMoimNotice(final long moimId) {
        return queryFactory.select(new QMoimNoticeSimpleResponse(
                        moimNoticeJpaEntity.id,
                        moimNoticeJpaEntity.title,
                        moimNoticeJpaEntity.createdAt
                ))
                .from(moimNoticeJpaEntity)
                .where(moimNoticeJpaEntity.moimId.eq(moimId))
                .fetch();
    }

    @Override
    public MoimNoticeJpaEntity getById(final long moimNoticeId) {
        return moimNoticeJpaRepository.findById(moimNoticeId)
                .orElseThrow(() -> new NotFoundException(NOTICE_NOT_FOUND_ERROR.message()));
    }

    @Override
    public void deleteById(final long moimNoticeId) {
        moimNoticeJpaRepository.deleteById(moimNoticeId);
    }
}
