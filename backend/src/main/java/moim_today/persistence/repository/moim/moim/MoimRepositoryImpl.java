package moim_today.persistence.repository.moim.moim;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.LockModeType;
import moim_today.domain.moim.MoimSortedFilter;
import moim_today.domain.moim.enums.MoimCategory;
import moim_today.dto.moim.moim.MoimDateResponse;
import moim_today.dto.moim.moim.MoimSimpleResponse;
import moim_today.dto.moim.moim.QMoimDateResponse;
import moim_today.dto.moim.moim.QMoimSimpleResponse;
import moim_today.dto.moim.moim.enums.MoimCategoryDto;
import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static moim_today.global.constant.SymbolConstant.PERCENT;
import static moim_today.global.constant.exception.MoimExceptionConstant.MOIM_NOT_FOUND_ERROR;
import static moim_today.persistence.entity.moim.moim.QMoimJpaEntity.moimJpaEntity;

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

    @Transactional
    @Override
    public MoimJpaEntity getByIdWithPessimisticLock(final long moimId) {
        return queryFactory
                .selectFrom(moimJpaEntity)
                .where(moimJpaEntity.id.eq(moimId))
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .fetchFirst();
    }

    @Override
    public List<MoimSimpleResponse> searchMoimBySearchParam(final String searchParam) {
        return queryFactory.select(new QMoimSimpleResponse(
                        moimJpaEntity.id,
                        moimJpaEntity.title,
                        moimJpaEntity.capacity,
                        moimJpaEntity.currentCount,
                        moimJpaEntity.imageUrl,
                        moimJpaEntity.moimCategory,
                        moimJpaEntity.displayStatus
                ))
                .from(moimJpaEntity)
                .where(moimJpaEntity.title.likeIgnoreCase(PERCENT.value() + searchParam.trim() + PERCENT.value()))
                .fetch();
    }

    @Override
    public List<MoimSimpleResponse> findAllMoimResponse(final MoimCategoryDto moimCategoryDto, final MoimSortedFilter moimSortedFilter) {

        return queryFactory.select(new QMoimSimpleResponse(
                        moimJpaEntity.id,
                        moimJpaEntity.title,
                        moimJpaEntity.capacity,
                        moimJpaEntity.currentCount,
                        moimJpaEntity.imageUrl,
                        moimJpaEntity.moimCategory,
                        moimJpaEntity.displayStatus
                ))
                .from(moimJpaEntity)
                .where(applyMoimCategoryFilter(moimCategoryDto))
                .orderBy(createOrderBySpecifier(moimSortedFilter))
                .fetch();
    }

    private BooleanExpression applyMoimCategoryFilter(final MoimCategoryDto moimCategoryDto) {
        if (moimCategoryDto == MoimCategoryDto.ALL) {
            return null;
        }
        MoimCategory moimCategory = MoimCategory.valueOf(moimCategoryDto.name());
        return moimJpaEntity.moimCategory.eq(moimCategory);
    }

    private OrderSpecifier<?> createOrderBySpecifier(final MoimSortedFilter moimSortedFilter) {
        if (moimSortedFilter == MoimSortedFilter.VIEWS) {
            return moimJpaEntity.views.desc();
        }
        return moimJpaEntity.createdAt.desc();
    }
}
