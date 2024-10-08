package moim_today.persistence.repository.meeting.meeting;

import com.querydsl.jpa.impl.JPAQueryFactory;
import moim_today.dto.admin.meeting.AdminMeetingResponse;
import moim_today.dto.admin.meeting.QAdminMeetingResponse;
import moim_today.dto.mail.QUpcomingMeetingNoticeResponse;
import moim_today.dto.mail.UpcomingMeetingNoticeResponse;
import moim_today.dto.meeting.meeting.JoinedMeetingDao;
import moim_today.dto.meeting.meeting.MeetingSimpleDao;
import moim_today.dto.meeting.meeting.QJoinedMeetingDao;
import moim_today.dto.meeting.meeting.QMeetingSimpleDao;
import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.meeting.meeting.MeetingJpaEntity;
import moim_today.persistence.entity.meeting.meeting.QMeetingJpaEntity;
import moim_today.persistence.entity.moim.moim.QMoimJpaEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static moim_today.global.constant.exception.MeetingExceptionConstant.MEETING_NOT_FOUND_ERROR;
import static moim_today.persistence.entity.email_subscribe.QEmailSubscribeJpaEntity.*;
import static moim_today.persistence.entity.meeting.joined_meeting.QJoinedMeetingJpaEntity.joinedMeetingJpaEntity;
import static moim_today.persistence.entity.meeting.meeting.QMeetingJpaEntity.meetingJpaEntity;
import static moim_today.persistence.entity.member.QMemberJpaEntity.memberJpaEntity;
import static moim_today.persistence.entity.moim.moim.QMoimJpaEntity.moimJpaEntity;

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
    public List<Long> findUpcomingMeetingIdsByMoimId(final long moimId, final LocalDate currentDate) {
        return queryFactory
                .select(meetingJpaEntity.id)
                .from(meetingJpaEntity)
                .where(meetingJpaEntity.moimId.eq(moimId)
                        .and(meetingJpaEntity.startDateTime.goe(currentDate.atStartOfDay())))
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
                .where(
                        meetingJpaEntity.moimId.eq(moimId)
                                .and(joinedMeetingJpaEntity.memberId.eq(memberId))
                )
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
                .where(
                        meetingJpaEntity.moimId.eq(moimId)
                                .and(meetingJpaEntity.startDateTime.after(currentDateTime))
                                .and(joinedMeetingJpaEntity.memberId.eq(memberId))
                )
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
                .where(
                        meetingJpaEntity.moimId.eq(moimId)
                        .and(meetingJpaEntity.startDateTime.before(currentDateTime))
                        .and(joinedMeetingJpaEntity.memberId.eq(memberId))
                )
                .orderBy(meetingJpaEntity.startDateTime.asc())
                .fetch();
    }

    @Override
    public List<JoinedMeetingDao> findAllByMeetingIds(final List<Long> meetingIds) {
        return queryFactory.select(
                        new QJoinedMeetingDao(
                                moimJpaEntity.id,
                                meetingJpaEntity.id,
                                moimJpaEntity.title,
                                meetingJpaEntity.agenda,
                                meetingJpaEntity.startDateTime
                        )
                )
                .from(meetingJpaEntity)
                .join(moimJpaEntity).on(meetingJpaEntity.moimId.eq(moimJpaEntity.id))
                .where(meetingJpaEntity.id.in(meetingIds))
                .orderBy(meetingJpaEntity.startDateTime.asc())
                .fetch();
    }

    @Override
    public List<JoinedMeetingDao> findAllUpcomingByMeetingIds(final List<Long> meetingIds,
                                                              final LocalDateTime currentDateTime) {
        return queryFactory.select(
                        new QJoinedMeetingDao(
                                moimJpaEntity.id,
                                meetingJpaEntity.id,
                                moimJpaEntity.title,
                                meetingJpaEntity.agenda,
                                meetingJpaEntity.startDateTime
                        )
                )
                .from(meetingJpaEntity)
                .join(moimJpaEntity).on(meetingJpaEntity.moimId.eq(moimJpaEntity.id))
                .where(meetingJpaEntity.id.in(meetingIds)
                        .and(meetingJpaEntity.startDateTime.after(currentDateTime)))
                .orderBy(meetingJpaEntity.startDateTime.asc())
                .fetch();
    }

    @Override
    public List<JoinedMeetingDao> findAllPastByMeetingIds(final List<Long> meetingIds,
                                                          final LocalDateTime currentDateTime) {
        return queryFactory.select(
                        new QJoinedMeetingDao(
                                moimJpaEntity.id,
                                meetingJpaEntity.id,
                                moimJpaEntity.title,
                                meetingJpaEntity.agenda,
                                meetingJpaEntity.startDateTime
                        )
                )
                .from(meetingJpaEntity)
                .join(moimJpaEntity).on(meetingJpaEntity.moimId.eq(moimJpaEntity.id))
                .where(meetingJpaEntity.id.in(meetingIds)
                        .and(meetingJpaEntity.startDateTime.before(currentDateTime)))
                .orderBy(meetingJpaEntity.startDateTime.asc())
                .fetch();
    }

    @Override
    public List<AdminMeetingResponse> findAllByAdminMoimId(final long moimId) {
        return queryFactory.select(
                        new QAdminMeetingResponse(
                                meetingJpaEntity.id,
                                meetingJpaEntity.agenda,
                                meetingJpaEntity.startDateTime,
                                meetingJpaEntity.endDateTime,
                                meetingJpaEntity.place
                        ))
                .from(meetingJpaEntity)
                .where(meetingJpaEntity.moimId.eq(moimId))
                .fetch();
    }

    @Override
    public long getHostIdByMeetingId(final long meetingId) {
        return queryFactory.select(moimJpaEntity.memberId)
                .from(meetingJpaEntity)
                .join(moimJpaEntity).on(moimJpaEntity.id.eq(meetingJpaEntity.moimId))
                .where(meetingJpaEntity.id.eq(meetingId))
                .stream().findAny()
                .orElseThrow(() -> new NotFoundException(MEETING_NOT_FOUND_ERROR.message()));
    }

    @Override
    public List<UpcomingMeetingNoticeResponse> findUpcomingNotices(final LocalDateTime currentDateTime) {
        LocalDateTime upcomingDateTime = currentDateTime.plusDays(1);

        return queryFactory.select(
                        new QUpcomingMeetingNoticeResponse(
                                meetingJpaEntity.moimId,
                                joinedMeetingJpaEntity.id,
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
                .innerJoin(emailSubscribeJpaEntity).on(memberJpaEntity.id.eq(emailSubscribeJpaEntity.memberId))
                .where(
                        meetingJpaEntity.startDateTime.loe(upcomingDateTime)
                                .and(meetingJpaEntity.startDateTime.after(currentDateTime)
                                        .and(joinedMeetingJpaEntity.upcomingNoticeSent.isFalse())
                                        .and(emailSubscribeJpaEntity.subscribeStatus.isTrue())
                                )
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

    @Override
    public long findMoimIdByMeetingId(final long meetingId) {
        return queryFactory.select(meetingJpaEntity.moimId)
                .from(meetingJpaEntity)
                .where(meetingJpaEntity.id.eq(meetingId))
                .stream().findAny()
                .orElseThrow(() -> new NotFoundException(MEETING_NOT_FOUND_ERROR.message()));
    }
}
