package moim_today.persistence.repository.meeting.meeting;

import com.querydsl.jpa.impl.JPAQueryFactory;
import moim_today.persistence.entity.meeting.meeting.QMeetingJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MeetingRepositoryImpl implements MeetingRepository {

    private final MeetingJpaRepository meetingJpaRepository;
    private final JPAQueryFactory queryFactory;

    public MeetingRepositoryImpl(final MeetingJpaRepository meetingJpaRepository,
                                 final JPAQueryFactory queryFactory) {
        this.meetingJpaRepository = meetingJpaRepository;
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Long> findAllByMoimId(final long moimId) {
        return queryFactory
                .select(QMeetingJpaEntity.meetingJpaEntity.id)
                .from(QMeetingJpaEntity.meetingJpaEntity)
                .where(QMeetingJpaEntity.meetingJpaEntity.moimId.eq(moimId))
                .fetch();
    }
}
