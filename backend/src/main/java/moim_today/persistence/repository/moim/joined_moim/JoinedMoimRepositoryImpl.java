package moim_today.persistence.repository.moim.joined_moim;

import com.querydsl.jpa.impl.JPAQueryFactory;
import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

import static moim_today.global.constant.exception.JoinedMoimExceptionConstant.JOINED_MOIM_MEMBER_IS_EMPTY;
import static moim_today.persistence.entity.moim.joined_moim.QJoinedMoimJpaEntity.joinedMoimJpaEntity;

@Repository
public class JoinedMoimRepositoryImpl implements JoinedMoimRepository {

    private final JoinedMoimJpaRepository joinedMoimJpaRepository;
    private final JPAQueryFactory queryFactory;

    public JoinedMoimRepositoryImpl(final JoinedMoimJpaRepository joinedMoimJpaRepository,
                                    final JPAQueryFactory queryFactory) {
        this.joinedMoimJpaRepository = joinedMoimJpaRepository;
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Long> findAllJoinedMemberId(final long moimId) {
        return queryFactory.select(joinedMoimJpaEntity.memberId)
                .from(joinedMoimJpaEntity)
                .where(joinedMoimJpaEntity.moimId.eq(moimId))
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
    public void deleteMoimMember(long moimId, long memberId) {
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
}
