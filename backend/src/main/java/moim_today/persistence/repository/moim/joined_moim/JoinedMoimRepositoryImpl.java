package moim_today.persistence.repository.moim.joined_moim;

import com.querydsl.jpa.impl.JPAQueryFactory;
import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;
import moim_today.persistence.entity.moim.joined_moim.QJoinedMoimJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static moim_today.persistence.entity.moim.joined_moim.QJoinedMoimJpaEntity.*;

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
    public void save(final JoinedMoimJpaEntity joinedMoimJpaEntity) {
        joinedMoimJpaRepository.save(joinedMoimJpaEntity);
    }

    @Override
    public long count() {
        return joinedMoimJpaRepository.count();
    }
}
