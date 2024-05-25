package moim_today.persistence.repository.moim.joined_moim;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import moim_today.dto.moim.moim.MoimSimpleResponse;
import moim_today.dto.moim.moim.QMoimSimpleResponse;
import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static moim_today.global.constant.NumberConstant.MAX_DASHBOARD_MOIM_DISPLAY_COUNT;
import static moim_today.global.constant.exception.MoimExceptionConstant.JOINED_MOIM_MEMBER_IS_EMPTY;
import static moim_today.persistence.entity.moim.joined_moim.QJoinedMoimJpaEntity.joinedMoimJpaEntity;
import static moim_today.persistence.entity.moim.moim.QMoimJpaEntity.moimJpaEntity;

@Repository
public class JoinedMoimRepositoryImpl implements JoinedMoimRepository {

    private final JoinedMoimJpaRepository joinedMoimJpaRepository;
    private final JPAQueryFactory queryFactory;

    public JoinedMoimRepositoryImpl(final JoinedMoimJpaRepository joinedMoimJpaRepository,
                                    final JPAQueryFactory queryFactory) {
        this.joinedMoimJpaRepository = joinedMoimJpaRepository;
        this.queryFactory = queryFactory;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Long> findAllJoinedMemberId(final long moimId) {
        return queryFactory.select(joinedMoimJpaEntity.memberId)
                .from(joinedMoimJpaEntity)
                .where(joinedMoimJpaEntity.moimId.eq(moimId))
                .fetch();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Long> findMoimIdsByMemberId(final long memberId) {
        return queryFactory.select(joinedMoimJpaEntity.moimId)
                .from(joinedMoimJpaEntity)
                .where(joinedMoimJpaEntity.memberId.eq(memberId))
                .fetch();
    }

    @Override
    public void deleteAllByMoimId(final long moimId) {
        joinedMoimJpaRepository.deleteAllByMoimId(moimId);
    }

    @Override
    public long count() {
        return joinedMoimJpaRepository.count();
    }

    @Override
    public JoinedMoimJpaEntity save(final JoinedMoimJpaEntity joinedMoimJpaEntity) {
        return joinedMoimJpaRepository.save(joinedMoimJpaEntity);
    }

    @Override
    public List<JoinedMoimJpaEntity> findJoinMembersByMoimId(final long moimId) {
        List<JoinedMoimJpaEntity> membersByMoimId = joinedMoimJpaRepository.findMembersByMoimId(moimId);
        if(membersByMoimId.isEmpty()){
            throw new NotFoundException(JOINED_MOIM_MEMBER_IS_EMPTY.message());
        }
        return membersByMoimId;
    }

    @Override
    public void deleteMoimMember(final long moimId, final long memberId) {
        joinedMoimJpaRepository.deleteByMoimIdAndMemberId(moimId,memberId);
    }

    @Override
    public boolean isJoining(final long moimId, final long memberId) {
        return joinedMoimJpaRepository.existsByMoimIdAndMemberId(moimId,memberId);
    }

    @Override
    public boolean existsByMoimIdAndMemberId(final long moimId, final long memberId) {
        return joinedMoimJpaRepository.existsByMoimIdAndMemberId(moimId, memberId);
    }

    @Override
    public List<MoimSimpleResponse> findAllMyJoinedMoimSimpleResponses(final long memberId,
                                                                       final long lastMoimId,
                                                                       final LocalDate now,
                                                                       final boolean ended) {
        return queryFactory.select(new QMoimSimpleResponse(
                        moimJpaEntity.id,
                        moimJpaEntity.title,
                        moimJpaEntity.capacity,
                        moimJpaEntity.currentCount,
                        moimJpaEntity.imageUrl,
                        moimJpaEntity.moimCategory,
                        moimJpaEntity.displayStatus
                ))
                .from(joinedMoimJpaEntity)
                .join(moimJpaEntity).on(joinedMoimJpaEntity.moimId.eq(moimJpaEntity.id))
                .where(
                        joinedMoimJpaEntity.memberId.eq(memberId),
                        applyEndedFilter(now, ended),
                        ltLastMoimId(lastMoimId)
                )
                .orderBy(moimJpaEntity.id.desc())
                .limit(MAX_DASHBOARD_MOIM_DISPLAY_COUNT.value())
                .fetch();
    }

    private BooleanExpression applyEndedFilter(final LocalDate now, final boolean ended) {
        if (ended) {
            return moimJpaEntity.endDate.before(now);
        }
        return moimJpaEntity.endDate.goe(now);
    }

    private BooleanExpression ltLastMoimId(final long lastMoimId) {
        if (lastMoimId == 0) {
            return null;
        }
        return moimJpaEntity.id.lt(lastMoimId);
    }
}
