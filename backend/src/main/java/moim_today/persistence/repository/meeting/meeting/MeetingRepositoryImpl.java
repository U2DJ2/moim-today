package moim_today.persistence.repository.meeting.meeting;

import com.querydsl.jpa.impl.JPAQueryFactory;
import moim_today.dto.mail.QUpcomingMeetingNoticeResponse;
import moim_today.dto.mail.UpcomingMeetingNoticeResponse;
import moim_today.dto.meeting.MeetingSimpleDao;
import moim_today.dto.meeting.QMeetingSimpleDao;
import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static moim_today.global.constant.exception.MeetingExceptionConstant.MEETING_NOT_FOUND_ERROR;
import static moim_today.persistence.entity.meeting.joined_meeting.QJoinedMeetingJpaEntity.*;
import static moim_today.persistence.entity.meeting.meeting.QMeetingJpaEntity.meetingJpaEntity;
import static moim_today.persistence.entity.member.QMemberJpaEntity.*;
import static moim_today.persistence.entity.moim.moim.QMoimJpaEntity.*;

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
    public List<MeetingSimpleDao> findAllByMoimId(final long moimId, final long memberId,
                                                  final LocalDateTime currentDateTime) {
        return queryFactory.select(
                        new QMeetingSimpleDao(
                                meetingJpaEntity.id,
                                meetingJpaEntity.agenda,
                                meetingJpaEntity.startDateTime,
                                joinedMeetingJpaEntity.attendance
                        ))
                .from(meetingJpaEntity)
                .join(joinedMeetingJpaEntity).on(joinedMeetingJpaEntity.meetingId.eq(meetingJpaEntity.id))
                .where(meetingJpaEntity.moimId.eq(moimId)
                        .and(joinedMeetingJpaEntity.memberId.eq(memberId)))
                .orderBy(meetingJpaEntity.startDateTime.asc())
                .fetch();
    }

    @Override
    public List<MeetingSimpleDao> findAllUpcomingByMoimId(final long moimId, final long memberId,
                                                          final LocalDateTime currentDateTime) {
        return queryFactory.select(
                        new QMeetingSimpleDao(
                                meetingJpaEntity.id,
                                meetingJpaEntity.agenda,
                                meetingJpaEntity.startDateTime,
                                joinedMeetingJpaEntity.attendance
                        ))
                .from(meetingJpaEntity)
                .join(joinedMeetingJpaEntity).on(joinedMeetingJpaEntity.meetingId.eq(meetingJpaEntity.id))
                .where(meetingJpaEntity.moimId.eq(moimId)
                        .and(meetingJpaEntity.startDateTime.after(currentDateTime))
                        .and(joinedMeetingJpaEntity.memberId.eq(memberId)))
                .orderBy(meetingJpaEntity.startDateTime.asc())
                .fetch();
    }

    @Override
    public List<MeetingSimpleDao> findAllPastByMoimId(final long moimId, final long memberId,
                                                      final LocalDateTime currentDateTime) {
        return queryFactory.select(
                        new QMeetingSimpleDao(
                                meetingJpaEntity.id,
                                meetingJpaEntity.agenda,
                                meetingJpaEntity.startDateTime,
                                joinedMeetingJpaEntity.attendance
                        ))
                .from(meetingJpaEntity)
                .join(joinedMeetingJpaEntity).on(joinedMeetingJpaEntity.meetingId.eq(meetingJpaEntity.id))
                .where(meetingJpaEntity.moimId.eq(moimId)
                        .and(meetingJpaEntity.startDateTime.before(currentDateTime))
                        .and(joinedMeetingJpaEntity.memberId.eq(memberId)))
                .orderBy(meetingJpaEntity.startDateTime.asc())
                .fetch();
    }

    @Override
    public long findHostIdByMeetingId(final long meetingId) {
        return queryFactory.select(moimJpaEntity.memberId)
                .from(meetingJpaEntity)
                .join(moimJpaEntity).on(moimJpaEntity.id.eq(meetingJpaEntity.moimId))
                .fetchFirst();
    }

    @Override
    public List<UpcomingMeetingNoticeResponse> findUpcomingNotices(final LocalDateTime currentDateTime) {
        LocalDateTime upcomingDateTime = currentDateTime.plusDays(1);

        return queryFactory.select(
                        new QUpcomingMeetingNoticeResponse(
                                joinedMeetingJpaEntity.id,
                                meetingJpaEntity.moimId,
                                memberJpaEntity.email,
                                meetingJpaEntity.agenda,
                                meetingJpaEntity.startDateTime,
                                meetingJpaEntity.endDateTime,
                                meetingJpaEntity.place,
                                joinedMeetingJpaEntity.attendance
                        )
                )
                .from(meetingJpaEntity)
                .innerJoin(joinedMeetingJpaEntity).on(joinedMeetingJpaEntity.meetingId.eq(meetingJpaEntity.id))
                .innerJoin(memberJpaEntity).on(memberJpaEntity.id.eq(joinedMeetingJpaEntity.memberId))
                .where(
                        meetingJpaEntity.startDateTime.loe(upcomingDateTime)
                                .and(meetingJpaEntity.startDateTime.after(currentDateTime)
                                        .and(joinedMeetingJpaEntity.upcomingNoticeSent.isFalse()))
                )
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
    public void delete(final MeetingJpaEntity meetingJpaEntity) {
        meetingJpaRepository.delete(meetingJpaEntity);
    }

    @Override
    public long count() {
        return meetingJpaRepository.count();
    }
}
