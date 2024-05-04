package moim_today.persistence.repository.meeting.joined_meeting;

import com.querydsl.jpa.impl.JPAQueryFactory;
import moim_today.persistence.entity.meeting.joined_meeting.JoinedMeetingJpaEntity;
import moim_today.persistence.entity.meeting.joined_meeting.QJoinedMeetingJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

import static moim_today.persistence.entity.meeting.joined_meeting.QJoinedMeetingJpaEntity.*;

@Repository
public class JoinedMeetingRepositoryImpl implements JoinedMeetingRepository {

    private final JoinedMeetingJpaRepository joinedMeetingJpaRepository;
    private final JPAQueryFactory queryFactory;

    public JoinedMeetingRepositoryImpl(final JoinedMeetingJpaRepository joinedMeetingJpaRepository,
                                       final JPAQueryFactory queryFactory) {
        this.joinedMeetingJpaRepository = joinedMeetingJpaRepository;
        this.queryFactory = queryFactory;
    }

    @Override
    public void deleteAllByMeetingIdIn(final List<Long> meetingIds) {
        joinedMeetingJpaRepository.deleteAllByMeetingIdIn(meetingIds);
    }

    @Override
    public void save(final JoinedMeetingJpaEntity joinedMeetingJpaEntity) {
        joinedMeetingJpaRepository.save(joinedMeetingJpaEntity);
    }

    @Override
    public void saveAll(final List<JoinedMeetingJpaEntity> joinedMeetingJpaEntities) {
        joinedMeetingJpaRepository.saveAll(joinedMeetingJpaEntities);
    }

    @Override
    public List<Long> findAllMemberIdByMeetingId(final long meetingId) {
        return queryFactory.select(joinedMeetingJpaEntity.memberId)
                .from(joinedMeetingJpaEntity)
                .where(joinedMeetingJpaEntity.meetingId.eq(meetingId))
                .fetch();
    }

    @Override
    public List<JoinedMeetingJpaEntity> findAll() {
        return joinedMeetingJpaRepository.findAll();
    }

    @Override
    public long count() {
        return joinedMeetingJpaRepository.count();
    }
}
