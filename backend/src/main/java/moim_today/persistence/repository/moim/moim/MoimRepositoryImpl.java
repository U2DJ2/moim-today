package moim_today.persistence.repository.moim.moim;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.LockModeType;
import moim_today.dto.moim.moim.MoimDateResponse;
import moim_today.dto.moim.moim.QMoimDateResponse;
import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static moim_today.global.constant.exception.MoimExceptionConstant.MOIM_NOT_FOUND_ERROR;
import static moim_today.persistence.entity.moim.moim.QMoimJpaEntity.*;

@Repository
public class MoimRepositoryImpl implements MoimRepository {

    private final MoimJpaRepository moimJpaRepository;
    private final JPAQueryFactory queryFactory;

    public MoimRepositoryImpl(final MoimJpaRepository moimJpaRepository,
                              final JPAQueryFactory queryFactory) {
        this.moimJpaRepository = moimJpaRepository;
        this.queryFactory = queryFactory;
    }

    @Override
    public MoimJpaEntity save(final MoimJpaEntity moimJpaEntity) {
        return moimJpaRepository.save(moimJpaEntity);
    }

    @Override
    public long count() {
        return moimJpaRepository.count();
    }

    @Override
    public MoimJpaEntity getById(final long moimId) {
        return moimJpaRepository.findById(moimId)
                .orElseThrow(() -> new NotFoundException(MOIM_NOT_FOUND_ERROR.message()));
    }

    @Override
    public String getTitleById(final long moimId) {
        return queryFactory.select(moimJpaEntity.title)
                .from(moimJpaEntity)
                .where(moimJpaEntity.id.eq(moimId))
                .fetchOne();
    }

    @Override
    public MoimDateResponse findMoimDate(final long moimId) {
        return queryFactory.select(
                        new QMoimDateResponse(
                                moimJpaEntity.startDate,
                                moimJpaEntity.endDate
                        ))
                .from(moimJpaEntity)
                .where(moimJpaEntity.id.eq(moimId))
                .fetchOne();
    }

    @Override
    public void deleteById(final long moimId) {
        moimJpaRepository.deleteById(moimId);
    }

    @Override
    public long getMemberIdById(final long moimId) {
        return queryFactory
                .select(moimJpaEntity.memberId)
                .from(moimJpaEntity)
                .where(moimJpaEntity.id.eq(moimId))
                .stream().findAny()
                .orElseThrow(() -> new NotFoundException(MOIM_NOT_FOUND_ERROR.message()));
    }

    @Transactional
    @Override
    public MoimJpaEntity getByIdWithPessimisticLock(final long moimId) {
        return queryFactory
                .selectFrom(moimJpaEntity)
                .where(moimJpaEntity.id.eq(moimId))
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .fetchFirst();
    }
}
