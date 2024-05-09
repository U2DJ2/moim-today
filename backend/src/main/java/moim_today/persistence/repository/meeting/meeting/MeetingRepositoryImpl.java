package moim_today.persistence.repository.meeting.meeting;

import com.querydsl.jpa.impl.JPAQueryFactory;
import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static moim_today.global.constant.exception.MeetingExceptionConstant.MEETING_NOT_FOUND_ERROR;
import static moim_today.persistence.entity.meeting.meeting.QMeetingJpaEntity.meetingJpaEntity;

@Repository
public class MeetingRepositoryImpl implements MeetingRepository {

    private final MeetingJpaRepository meetingJpaRepository;
    private final JPAQueryFactory queryFactory;

    public MeetingRepositoryImpl(final MeetingJpaRepository meetingJpaRepository,
                                 final JPAQueryFactory queryFactory) {
        this.meetingJpaRepository = meetingJpaRepository;
        this.queryFactory = queryFactory;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Long> findMeetingIdsByMoimId(final long moimId) {
        return queryFactory
                .select(meetingJpaEntity.id)
                .from(meetingJpaEntity)
                .where(meetingJpaEntity.moimId.eq(moimId))
                .fetch();
    }

    @Override
    public List<MeetingJpaEntity> findAllUpcomingByMoimId(final long moimId, final LocalDateTime currentDateTime) {
        return queryFactory.selectFrom(meetingJpaEntity)
                .where(meetingJpaEntity.moimId.eq(moimId)
                        .and(meetingJpaEntity.startDateTime.after(currentDateTime)))
                .fetch();
    }

    @Override
    public List<MeetingJpaEntity> findAllPastByMoimId(final long moimId, final LocalDateTime currentDateTime) {
        return queryFactory.selectFrom(meetingJpaEntity)
                .where(meetingJpaEntity.moimId.eq(moimId)
                        .and(meetingJpaEntity.startDateTime.before(currentDateTime)))
                .fetch();
    }

    @Override
    public MeetingJpaEntity getById(final long meetingId) {
        return meetingJpaRepository.findById(meetingId)
                .orElseThrow(() -> new NotFoundException(MEETING_NOT_FOUND_ERROR.message()));
    }

    @Override
    public MeetingJpaEntity save(final MeetingJpaEntity meetingJpaEntity) {
        return meetingJpaRepository.save(meetingJpaEntity);
    }

    @Override
    public long count() {
        return meetingJpaRepository.count();
    }
}
